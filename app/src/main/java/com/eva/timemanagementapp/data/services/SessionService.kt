package com.eva.timemanagementapp.data.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.content.getSystemService
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.TimerServiceRepository
import com.eva.timemanagementapp.domain.stopwatch.TimerWatch
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.utils.NotificationConstants
import com.eva.timemanagementapp.utils.Resource
import com.eva.timemanagementapp.utils.ServiceConstants
import com.eva.timemanagementapp.utils.SessionServiceActions
import com.eva.timemanagementapp.utils.extensions.toHMSFormat
import com.eva.timemanagementapp.utils.extensions.toLocalTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SERVICE_LOG_TAG = "SERVICE_LOG"

@AndroidEntryPoint
class SessionService : Service() {

	inner class ServiceBinder : Binder() {

		val service: SessionService = this@SessionService
	}

	private val binder = ServiceBinder()

	private val notificationManager by lazy { getSystemService<NotificationManager>() }

	private val scope by lazy { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

	@Inject
	lateinit var notificationHelper: NotificationBuilderHelper

	@Inject
	lateinit var preferences: SettingsPreferences

	@Inject
	lateinit var timerRepository: TimerServiceRepository


	lateinit var stopWatch: TimerWatch

	private val _timerMode = MutableStateFlow(TimerModes.FOCUS_MODE)
	val timerMode = _timerMode.asStateFlow()

	private val _timerDuration = MutableStateFlow(DurationOption.ONE_MINUTE)
	val timerDuration = _timerDuration.map { option -> option.minutes.toLocalTime() }

	override fun onBind(intent: Intent): IBinder = binder

	private fun updateDurationAndUpdateTimer() = scope.launch(Dispatchers.Main) {
		combine(
			preferences.focusDuration, preferences.breakDuration, _timerMode, stopWatch.state
		) { focusDuration, breakDuration, mode, state ->
			if (state != TimerWatchStates.IDLE) return@combine
			when (mode) {
				TimerModes.FOCUS_MODE -> {
					_timerDuration.update { focusDuration }
					stopWatch.setTime(focusDuration.minutes)
				}

				TimerModes.BREAK_MODE -> {
					_timerDuration.update { breakDuration }
					stopWatch.setTime(breakDuration.minutes)
				}
			}
		}.launchIn(this)
	}

	private fun addSessionsIntoDataBase() = scope.launch(Dispatchers.IO) {
		combine(
			_timerMode, stopWatch.state, preferences.isSaveSessions
		) { mode, state, isSavable ->
			if (state != TimerWatchStates.COMPLETED || !isSavable) return@combine

			// Not using the _timerDuration and _timerMode in combine
			// As it mostly change in IDLE state of the watch
			// And here we are considering if the state is COMPLETED
			val isSave = timerRepository.addTimerSession(
				mode = mode,
				option = _timerDuration.value
			)

			when (isSave) {
				is Resource.Error -> Log.e(
					SERVICE_LOG_TAG,
					"ERROR_OCCURRED_WHILE_SAVING :${isSave.errorMessage}"
				)

				is Resource.Success -> Log.i(
					SERVICE_LOG_TAG,
					"SAVED :${mode} FOR ${_timerDuration.value.minutes}"
				)
			}
		}.launchIn(this)
	}

	private fun updateNotificationData() = scope.launch(Dispatchers.Main) {
		combine(stopWatch.state, stopWatch.elapsedTime) { state, time ->
			when (state) {
				TimerWatchStates.RUNNING -> {
					val formattedTIme = time.toHMSFormat()
					val notification = notificationHelper.setContentTitle(formattedTIme)

					notificationManager?.notify(
						NotificationConstants.NOTIFICATION_ID,
						notification.build()
					)
				}

				TimerWatchStates.COMPLETED -> {
					val notification = notificationHelper.setCompletedNotification(
						title = NotificationConstants.TIMER_COMPLETED_TITLE,
						text = NotificationConstants.TIMER_COMPLETED_TEXT
					)

					notificationManager?.notify(
						NotificationConstants.NOTIFICATION_ID,
						notification.build()
					)
					_timerMode.update(TimerModes::switchModes)
					//setting the idle is important as it sets the duration for the next focus or break duration
					stopWatch.setModeIdle()
				}

				else -> {}
			}
		}.launchIn(scope)
	}

	override fun onCreate() {
		super.onCreate()
		Log.i(SERVICE_LOG_TAG, "CREATED")

		stopWatch = TimerWatch(scope = scope, interval = 800L)
		try {
			//updates the duration timer
			updateDurationAndUpdateTimer()
			// adds the session info into the database
			addSessionsIntoDataBase()
			// updated the notification
			updateNotificationData()
		} catch (e: Exception) {
			Log.e(SERVICE_LOG_TAG, e.message ?: "SOME_ERROR_ON_CREATE")
			e.printStackTrace()
		}
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		when (intent.action) {
			SessionServiceActions.START_TIMER.action -> {

				when (intent.getStringExtra(ServiceConstants.START_TIMER_MODE)) {
					ServiceConstants.START_TIMER_FOCUS_MODE -> startStopWatch(
						mode = TimerModes.FOCUS_MODE
					)

					ServiceConstants.START_TIMER_BREAK_MODE -> startStopWatch(
						mode = TimerModes.BREAK_MODE
					)
				}
			}

			SessionServiceActions.PAUSE_TIMER.action -> pauseTimer()
			SessionServiceActions.STOP_TIMER.action -> stopTimer()
			SessionServiceActions.RESUME_TIMER.action -> onResumeTimer()
			SessionServiceActions.DISMISS_TIMER_NOTIFICATION.action -> dismissNotification()
		}

		return super.onStartCommand(intent, flags, startId)
	}

	private fun dismissNotification() {
		stopForeground(STOP_FOREGROUND_REMOVE)
		// If the app is not in foreground the stop-self will stop the service
		stopSelf()
	}


	private fun startStopWatch(mode: TimerModes = TimerModes.FOCUS_MODE) {
		_timerMode.updateAndGet { mode }
		when (mode) {
			TimerModes.FOCUS_MODE -> notificationHelper
				.setContentText(NotificationConstants.TIMER_RUNNING_FOCUS_MODE_TEXT)

			TimerModes.BREAK_MODE -> notificationHelper
				.setContentText(NotificationConstants.TIMER_RUNNING_BREAK_MODE_TEXT)
		}

		notificationHelper.setPauseAction()
		startForeground(
			NotificationConstants.NOTIFICATION_ID,
			notificationHelper.notification
		)

		stopWatch.start()
	}

	private fun pauseTimer() {
		Log.i(SERVICE_LOG_TAG, "PAUSE_TIMER")
		stopWatch.onPause()

		val updatedNotification = notificationHelper
			.setResumeAction()
			.setContentText(NotificationConstants.PAUSED_NOTIFICATION_TITLE)

		notificationManager?.notify(
			NotificationConstants.NOTIFICATION_ID,
			updatedNotification.build()
		)
	}

	private fun onResumeTimer() {
		Log.i(SERVICE_LOG_TAG, "RESUME_TIMER")
		stopWatch.onResume()

		val updatedNotification = notificationHelper
			.setPauseAction()
			.apply {
				if (_timerMode.value == TimerModes.FOCUS_MODE)
					setContentText(NotificationConstants.TIMER_RUNNING_FOCUS_MODE_TEXT)
				else setContentText(NotificationConstants.TIMER_RUNNING_BREAK_MODE_TEXT)
			}

		notificationManager?.notify(
			NotificationConstants.NOTIFICATION_ID,
			updatedNotification.build()
		)
	}

	private fun stopTimer() {
		Log.i(SERVICE_LOG_TAG, "STOP_TIMER")
		_timerMode.update { TimerModes.FOCUS_MODE }
		stopWatch.onReset()
		stopForeground(STOP_FOREGROUND_REMOVE)
		// if the app is not in foreground kill the service if stop is clicked
		stopSelf()
	}

	override fun onDestroy() {
		stopWatch.cancelTimerJob()
		scope.cancel()
		Log.i(SERVICE_LOG_TAG, "DESTROYED")
		super.onDestroy()
	}
}