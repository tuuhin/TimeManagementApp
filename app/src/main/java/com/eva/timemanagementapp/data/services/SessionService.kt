package com.eva.timemanagementapp.data.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.eva.timemanagementapp.domain.stopwatch.TimerWatch
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.utils.NotificationConstants
import com.eva.timemanagementapp.utils.SessionServiceActions
import com.eva.timemanagementapp.utils.extensions.toHMSFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
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

	lateinit var stopWatch: TimerWatch

	private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

	override fun onBind(intent: Intent): IBinder = binder

	override fun onCreate() {
		super.onCreate()
		stopWatch = TimerWatch(scope = scope, interval = 500L)

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

				else -> {}
			}
		}.launchIn(scope)

		Log.i(SERVICE_LOG_TAG, "CREATED AND LISTENER SET")
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		when (intent.action) {
			SessionServiceActions.START_TIMER.action -> {
				val minutes = intent.getIntExtra("MINUTES", 2)
				startStopWatch(minutes = minutes)
			}

			SessionServiceActions.PAUSE_TIMER.action -> pauseTimer()
			SessionServiceActions.STOP_TIMER.action -> stopTimer()
			SessionServiceActions.RESUME_TIMER.action -> onResumeTimer()
		}

		return super.onStartCommand(intent, flags, startId)
	}

	private fun startStopWatch(minutes: Int) {
		startForeground(
			NotificationConstants.NOTIFICATION_ID,
			notificationHelper.notification
		)
		notificationHelper.setContentText(NotificationConstants.TIMER_RUNNING_TEXT)
		notificationHelper.setPauseAction()

		stopWatch.start(time = LocalTime.of(0, minutes))
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
		stopSelf()
	}

	override fun onDestroy() {
		stopWatch.cancelTimerJob()
		scope.cancel()
		Log.i(SERVICE_LOG_TAG, "DESTROYED")
		super.onDestroy()
	}
}