package com.eva.timemanagementapp.data.services

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.content.getSystemService
import com.eva.timemanagementapp.domain.stopwatch.StopWatch
import com.eva.timemanagementapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

private const val SERVICE_LOG_TAG = "SERVICE_LOG"

@AndroidEntryPoint
class SessionService : Service() {

	inner class SessionServiceBinder : Binder() {

		val service: SessionService = this@SessionService
	}

	private val binder = SessionServiceBinder()

	@Inject
	lateinit var helper: NotificationBuilderHelper

	private val notificationManager by lazy { getSystemService<NotificationManager>() }

	private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
	private lateinit var stopWatch: StopWatch

	override fun onBind(intent: Intent): IBinder = binder

	override fun onCreate() {
		super.onCreate()
		stopWatch = StopWatch(scope)
	}


	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		Log.d(SERVICE_LOG_TAG, "ON START COMMAND")
		when (intent.action) {
			SessionServiceActions.START_SESSION_STOPWATCH.action -> {
				startStopWatch()
			}

			SessionServiceActions.PAUSE_SESSION_STOPWATCH.action -> pauseStopWatch()
			SessionServiceActions.STOP_SESSION_STOPWATCH.action -> stopStopWatch()
		}
		return super.onStartCommand(intent, flags, startId)
	}


	private fun startStopWatch() {
		startForeground(Constants.NOTIFICATION_ID, helper.notification)
	}

	private fun stopStopWatch() {
		//stopWatch.onPause()
		helper.setStartStopAction()
	}

	private fun pauseStopWatch() {
		//	stopWatch.onPause()
		helper.setStartStopAction()
		notificationManager?.notify(Constants.NOTIFICATION_ID, helper.notification)
	}

	override fun onDestroy() {
		scope.cancel()
		super.onDestroy()
	}
}