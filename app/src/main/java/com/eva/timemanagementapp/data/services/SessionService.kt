package com.eva.timemanagementapp.data.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import com.eva.timemanagementapp.domain.models.SessionDurationOption
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.stopwatch.TimerWatch
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.utils.NotificationConstants
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
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import javax.inject.Inject

private const val SERVICE_LOG_TAG = "SERVICE_LOG"

@AndroidEntryPoint
class SessionService : Service() {

	inner class ServiceBinder : Binder() {

		val service: SessionService = this@SessionService
	}

	private val binder = ServiceBinder()

	@Inject
	lateinit var notificationHelper: NotificationBuilderHelper

	@Inject
	lateinit var notificationManager: NotificationManager

	@Inject
	lateinit var preferences: SettingsPreferences


	lateinit var stopWatch: TimerWatch

	private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

	private val _timerMode = MutableStateFlow(TimerModes.FOCUS_MODE)
	val timerMode = _timerMode.asStateFlow()

	private val _timerDuration = MutableStateFlow(LocalTime.MIN)
	val timerDuration = _timerDuration.asStateFlow()

	override fun onBind(intent: Intent): IBinder = binder


	override fun onCreate() {
		super.onCreate()

		stopWatch = TimerWatch(
			scope = scope,
			interval = 600L,
			initialTimerInMillis = 5000L
		)

		combine(
			preferences.focusDuration,
			preferences.breakDuration,
			_timerMode,
			stopWatch.state
		) { focusDuration, breakDuration, mode, state ->
			if (state == TimerWatchStates.IDLE) {
				when (mode) {
					TimerModes.FOCUS_MODE -> {
						_timerDuration.update { focusDuration.minutes.toLocalTime() }
						stopWatch.setTime(focusDuration.minutes)
					}

					TimerModes.BREAK_MODE -> {
						_timerDuration.update { breakDuration.minutes.toLocalTime() }
						stopWatch.setTime(breakDuration.minutes)
					}
				}
			}
		}.launchIn(scope)


		combine(stopWatch.state, stopWatch.elapsedTime) { state, time ->
			when (state) {
				TimerWatchStates.RUNNING -> {
					val formattedTIme = time.toHMSFormat()
					val notification = notificationHelper.setContentTitle(formattedTIme)

					notificationManager.notify(
						NotificationConstants.NOTIFICATION_ID,
						notification.build()
					)
				}

				TimerWatchStates.COMPLETED -> {
					//Marks it as complete

					Log.d(SERVICE_LOG_TAG, "COMPLETED")

					val notification = notificationHelper.setCompletedNotification(
						title = NotificationConstants.TIMER_COMPLETED_TITLE,
						text = NotificationConstants.TIMER_COMPLETED_TEXT
					)

					notificationManager.notify(
						NotificationConstants.NOTIFICATION_ID,
						notification.build()
					)
					_timerMode.update(TimerModes::switchModes)
					//setting the idle is important
					stopWatch.setModeIdle()
				}

				else -> {}
			}
		}.launchIn(scope)
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		when (intent.action) {
			SessionServiceActions.START_TIMER.action -> {

				val minutes = intent.getIntExtra(
					ServiceConstants.START_MINUTES_EXTRA_KEY,
					SessionDurationOption.ONE_MINUTE.minutes
				)

				when (intent.getStringExtra(ServiceConstants.START_TIMER_MODE)) {
					ServiceConstants.START_TIMER_FOCUS_MODE -> startStopWatch(
						minutes = minutes,
						mode = TimerModes.FOCUS_MODE
					)

					ServiceConstants.START_TIMER_BREAK_MODE -> startStopWatch(
						minutes,
						mode = TimerModes.BREAK_MODE
					)
				}
			}

			SessionServiceActions.PAUSE_TIMER.action -> pauseTimer()
			SessionServiceActions.STOP_TIMER.action -> stopTimer()
			SessionServiceActions.RESUME_TIMER.action -> onResumeTimer()
			SessionServiceActions.STOP_SESSION.action -> onStopSession()
		}

		return super.onStartCommand(intent, flags, startId)
	}

	private fun onStopSession() {
		stopWatch.onReset()
		stopForeground(STOP_FOREGROUND_REMOVE)
		stopSelf()
	}

	private fun startStopWatch(minutes: Int, mode: TimerModes = TimerModes.FOCUS_MODE) {
		_timerMode.update { oldMode -> if (oldMode == mode) oldMode else mode }

		notificationHelper.setContentText(NotificationConstants.TIMER_RUNNING_TEXT)
		notificationHelper.setPauseAction()
		startForeground(
			NotificationConstants.NOTIFICATION_ID,
			notificationHelper.notification
		)

		stopWatch.start()
	}

	private fun pauseTimer() {
		stopWatch.onPause()

		val updatedNotification = notificationHelper
			.setResumeAction()
			.setContentText(NotificationConstants.PAUSED_NOTIFICATION_TITLE)

		notificationManager.notify(
			NotificationConstants.NOTIFICATION_ID,
			updatedNotification.build()
		)
	}

	private fun onResumeTimer() {
		stopWatch.onResume()

		val updatedNotification = notificationHelper
			.setPauseAction()
			.setContentText(NotificationConstants.TIMER_RUNNING_TEXT)

		notificationManager.notify(
			NotificationConstants.NOTIFICATION_ID,
			updatedNotification.build()
		)
	}

	private fun stopTimer() {
		stopWatch.onReset()
		stopForeground(STOP_FOREGROUND_REMOVE)
		_timerMode.update { TimerModes.FOCUS_MODE }
		stopSelf()
	}

	override fun onDestroy() {
		stopWatch.cancelTimerJob()
		scope.cancel()
		Log.i(SERVICE_LOG_TAG, "DESTROYED")
		super.onDestroy()
	}
}