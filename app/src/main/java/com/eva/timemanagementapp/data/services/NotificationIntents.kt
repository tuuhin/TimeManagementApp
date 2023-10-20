package com.eva.timemanagementapp.data.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.eva.timemanagementapp.MainActivity
import com.eva.timemanagementapp.utils.RequestCodes
import com.eva.timemanagementapp.utils.SessionServiceActions

class NotificationIntents(
	private val context: Context
) {

	private val serviceIntent = Intent(context, SessionService::class.java)
	private val activityIntent = Intent(context, MainActivity::class.java)

	private fun buildServicePendingIntent(
		context: Context,
		requestCodes: RequestCodes,
		intent: Intent,
		flags: Int = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
	) = PendingIntent.getService(context, requestCodes.code, intent, flags)

	private fun buildActivityIntent(
		context: Context,
		requestCodes: RequestCodes,
		intent: Intent,
		flags: Int = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
	) = PendingIntent.getActivity(context, requestCodes.code, intent, flags)

	val pausePendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.PAUSE_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.PAUSE_TIMER.action
			},
		)


	val stopPendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.STOP_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.STOP_TIMER.action
			},
		)

	val dismissPendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.DISMISS_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.DISMISS_TIMER_NOTIFICATION.action
			}
		)

	val resumePendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.RESUME_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.RESUME_TIMER.action
			},
		)

	val startPendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.START_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.START_TIMER.action
			},
		)

	val contentActivityIntent: PendingIntent
		get() = buildActivityIntent(
			context = context,
			requestCodes = RequestCodes.START_ACTIVITY,
			intent = activityIntent.apply {
				flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
			}
		)
}