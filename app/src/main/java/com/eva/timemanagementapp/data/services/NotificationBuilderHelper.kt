package com.eva.timemanagementapp.data.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.eva.timemanagementapp.R

class NotificationBuilderHelper(
	private val context: Context,
) {
	private val intents by lazy { NotificationIntents(context) }

	private fun buildNotificationAction(
		title: String,
		intent: PendingIntent? = null
	) = NotificationCompat.Action.Builder(0, title, intent).build()

	private val pauseAction: NotificationCompat.Action
		get() = buildNotificationAction(
			title = context.getString(R.string.notification_action_pause),
			intent = intents.pausePendingIntent
		)

	private val resumeAction: NotificationCompat.Action
		get() = buildNotificationAction(
			title = context.getString(R.string.notification_action_resume),
			intent = intents.resumePendingIntent
		)


	private val stopAction: NotificationCompat.Action
		get() = buildNotificationAction(
			title = context.getString(R.string.notification_action_stop),
			intent = intents.stopPendingIntent
		)


	private var _notificationBuilder =
		NotificationCompat.Builder(context, context.getString(R.string.channel_id))
			.setSmallIcon(R.drawable.ic_timer)
			.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
			.setOngoing(true)
			.setCategory(Intent.CATEGORY_INFO)
			.setOnlyAlertOnce(true)
			.setContentIntent(intents.contentActivityIntent)
			.addAction(pauseAction)
			.addAction(stopAction)


	val notification: Notification
		get() = _notificationBuilder.build()


	@SuppressLint("RestrictedApi")
	fun setPauseAction(): NotificationCompat.Builder {
		return _notificationBuilder.apply {
			val actions = arrayListOf(pauseAction, stopAction)
			mActions = actions
		}
	}

	@SuppressLint("RestrictedApi")
	fun setResumeAction(): NotificationCompat.Builder {
		return _notificationBuilder.apply {
			val actions = arrayListOf(resumeAction, stopAction)
			mActions = actions
		}
	}

	fun setContentTitle(title: String) = _notificationBuilder
		.setContentTitle(title)


	fun setContentText(text: String) = _notificationBuilder
		.setContentText(text)

}