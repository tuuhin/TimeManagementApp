package com.eva.timemanagementapp.data.services

import android.content.Context
import android.content.Intent
import com.eva.timemanagementapp.utils.ServiceConstants
import com.eva.timemanagementapp.utils.SessionServiceActions

/**
 * A controller class to control the service from the ui
 */
class UIServiceController(
	private val context: Context
) {
	private val serviceIntent by lazy { Intent(context, SessionService::class.java) }

	fun startTimer(minutes: Int) {
		serviceIntent.apply {
			action = SessionServiceActions.START_TIMER.action
			putExtra(ServiceConstants.START_MINUTES_EXTRA_KEY, minutes)
			context.startService(this)
		}
	}

	fun pauseTimer() {
		serviceIntent.apply {
			action = SessionServiceActions.PAUSE_TIMER.action
			context.startService(this)
		}
	}

	fun resumeTimer() {
		serviceIntent.apply {
			action = SessionServiceActions.RESUME_TIMER.action
			context.startService(this)
		}
	}
}