package com.eva.timemanagementapp.data.services

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.eva.timemanagementapp.R

class NotificationBuilderHelper(
	private val context: Context,
) : NotificationIntents(context) {

	private fun buildNotificationAction(
		icon: Int,
		title: String,
		intent: PendingIntent? = null
	) = NotificationCompat.Action.Builder(icon, title, intent).build()

	private val pauseAction: NotificationCompat.Action
		get() = buildNotificationAction(
			icon = 0,
			title = context.getString(R.string.notification_action_pause),
			intent = pausePendingIntent
		)

	private val startAction: NotificationCompat.Action
		get() = buildNotificationAction(
			icon = 1,
			title = context.getString(R.string.notification_action_start),
			intent = startPendingIntent
		)


	private val stopAction: NotificationCompat.Action
		get() = buildNotificationAction(
			icon = 2,
			title = context.getString(R.string.notification_action_stop),
			intent = stopPendingIntent
		)


	private var _notificationBuilder =
		NotificationCompat.Builder(context, context.getString(R.string.channel_id))
			.setSmallIcon(R.drawable.ic_timer)
			.setOngoing(true)
			.setOnlyAlertOnce(true)
			.setContentIntent(contentActivityIntent)
			.addAction(pauseAction)
			.addAction(stopAction)

	val notification: Notification
		get() = _notificationBuilder.build()

	fun setPauseStopAction() = _notificationBuilder.clearActions()
		.addAction(pauseAction)
		.addAction(stopAction)
		.build()


	fun setContentTitle(title: String) = _notificationBuilder
		.setContentTitle(title)
		.build()

	fun setContentText(text: String) = _notificationBuilder
		.setContentText(text)
		.build()

	fun setOnGoing(onGoing: Boolean) = _notificationBuilder
		.setOngoing(onGoing)
		.build()

	fun setStartStopAction() = _notificationBuilder.clearActions()
		.addAction(startAction)
		.addAction(stopAction)
		.build()
}