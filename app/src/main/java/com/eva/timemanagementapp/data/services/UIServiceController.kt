package com.eva.timemanagementapp.data.services

import android.content.Context
import android.content.Intent
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.utils.ServiceConstants
import com.eva.timemanagementapp.utils.SessionServiceActions

/**
 * A controller class to control the service from the ui
 */
class UIServiceController(
	private val context: Context
) {
	private val serviceIntent by lazy { Intent(context, SessionService::class.java) }

	fun startTimer(minutes: Int, mode: TimerModes) {
		serviceIntent.apply {
			action = SessionServiceActions.START_TIMER.action

			putExtra(ServiceConstants.START_MINUTES_EXTRA_KEY, minutes)
			when (mode) {
				TimerModes.FOCUS_MODE -> putExtra(
					ServiceConstants.START_TIMER_MODE,
					ServiceConstants.START_TIMER_FOCUS_MODE
				)

				TimerModes.BREAK_MODE -> putExtra(
					ServiceConstants.START_TIMER_MODE,
					ServiceConstants.START_TIMER_BREAK_MODE
				)
			}

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

	fun stopTimer() {
		serviceIntent.apply {
			action = SessionServiceActions.STOP_TIMER.action
			context.startService(this)
		}
	}

}