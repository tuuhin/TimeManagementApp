package com.eva.timemanagementapp.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import com.eva.timemanagementapp.data.receiver.DailyGoalReminderReceiver
import com.eva.timemanagementapp.domain.facade.SessionReminderFacade
import com.eva.timemanagementapp.utils.RequestCodes
import java.time.LocalTime
import java.util.Calendar

class SessionReminderAlarm(
	private val context: Context,
) : SessionReminderFacade {

	private val alarmManager by lazy { context.getSystemService<AlarmManager>() }

	override fun setGoalReminderAlarm(time: LocalTime) = setRepeatingAlarm(time)

	override fun stopAlarm() = alarmManager?.cancel(alarmIntent) ?: Unit

	private fun setRepeatingAlarm(time: LocalTime) {
		val calender = Calendar.getInstance()
			.apply {
				add(Calendar.HOUR, time.hour)
				add(Calendar.MINUTE, time.minute)
			}

		val timeInMillis = calender.timeInMillis

		alarmManager?.setInexactRepeating(
			AlarmManager.RTC_WAKEUP,
			timeInMillis,
			AlarmManager.INTERVAL_DAY,
			alarmIntent
		)
	}

	private val alarmIntent: PendingIntent
		get() = PendingIntent.getBroadcast(
			context,
			RequestCodes.REMINDER_ALARM.code,
			Intent(context, DailyGoalReminderReceiver::class.java),
			PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
		)

}