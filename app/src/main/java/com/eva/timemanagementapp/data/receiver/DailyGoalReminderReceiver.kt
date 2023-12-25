package com.eva.timemanagementapp.data.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.eva.timemanagementapp.MainActivity
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import com.eva.timemanagementapp.utils.NotificationConstants
import com.eva.timemanagementapp.utils.RequestCodes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val ANDROID_LOGGER = "SESSION_REMINDER_RECEIVER"

@AndroidEntryPoint
class DailyGoalReminderReceiver : BroadcastReceiver() {

	@Inject
	lateinit var preferences: SettingsPreferences

	@Inject
	lateinit var sessionDao: SessionInfoDao

	private suspend fun computeNotificationInfo(): SessionNotificationInfo =
		withContext(Dispatchers.Default) {
			val sessionCount = sessionDao.getSessionCountToday()
			val sessionGoal = preferences.sessionCount.first().number
			val isSuccess = sessionCount > sessionGoal
			return@withContext SessionNotificationInfo(
				isSuccess = isSuccess,
				difference = if (isSuccess) 0 else sessionGoal - sessionCount
			)
		}

	private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

	override fun onReceive(context: Context, intent: Intent) {

		val notificationManager = context.getSystemService<NotificationManager>()

		val contentActivityIntent: PendingIntent = PendingIntent.getActivity(
			context,
			RequestCodes.START_ACTIVITY.code,
			Intent(context, MainActivity::class.java).apply {
				flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
			},
			PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
		)

		scope.launch {
			try {
				val notificationData = computeNotificationInfo()
				val text = if (notificationData.isSuccess)
					context.getString(R.string.reminder_notification_text)
				else "You still need to sit for ${notificationData.difference} sessions to reach today's goal"

				val notification =
					NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
						.setContentTitle(context.getString(R.string.reminder_notification_title))
						.setSmallIcon(R.drawable.ic_timer)
						.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
						.setPriority(NotificationCompat.PRIORITY_DEFAULT)
						.setOngoing(false)
						.setContentText(text)
						.setContentIntent(contentActivityIntent)
						.build()

				notificationManager?.notify(
					NotificationConstants.REMINDER_NOTIFICATION_ID,
					notification
				)
				Log.d(ANDROID_LOGGER, "NOTIFICATION_SHOWN")
			} catch (e: Exception) {
				Log.d(ANDROID_LOGGER, e.message ?: "")
				e.printStackTrace()
			} finally {
				Log.d(ANDROID_LOGGER, "CANCELED_NOTIFICATION")
				scope.cancel()
			}
		}
	}
}


private data class SessionNotificationInfo(
	val isSuccess: Boolean,
	val difference: Int = 0,
)