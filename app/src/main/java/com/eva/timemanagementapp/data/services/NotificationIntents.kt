package com.eva.timemanagementapp.data.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.eva.timemanagementapp.MainActivity

abstract class NotificationIntents(
	private val context: Context
) {

	private val serviceIntent = Intent(context, SessionService::class.java)
	private val activityIntent = Intent(context, MainActivity::class.java)

	private fun buildServicePendingIntent(
		context: Context,
		requestCodes: RequestCodes,
		intent: Intent,
		flags: Int = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
	) = PendingIntent.getService(context, requestCodes.code, intent, flags)

	private fun buildActivityIntent(
		context: Context,
		requestCodes: RequestCodes,
		intent: Intent,
		flags: Int = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
	) = PendingIntent.getActivity(context, requestCodes.code, intent, flags)

	protected val pausePendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.PAUSE_SESSION_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.PAUSE_SESSION_STOPWATCH.action
			},
		)


	protected val stopPendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.STOP_SESSION_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.STOP_SESSION_STOPWATCH.action
			},
		)

	protected val startPendingIntent: PendingIntent
		get() = buildServicePendingIntent(
			context = context,
			requestCodes = RequestCodes.START_SESSION_TIMER,
			intent = serviceIntent.apply {
				action = SessionServiceActions.START_SESSION_STOPWATCH.action
			},
		)

	protected val contentActivityIntent: PendingIntent
		get() = buildActivityIntent(
			context = context,
			requestCodes = RequestCodes.START_ACTIVITY,
			intent = activityIntent
		)
}