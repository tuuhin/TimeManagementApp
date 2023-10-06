package com.eva.timemanagementapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeManagementApp : Application() {

	private val notificationManager by lazy { getSystemService<NotificationManager>() }
	override fun onCreate() {
		super.onCreate()

		val channel = NotificationChannel(
			getString(R.string.channel_id),
			getString(R.string.channel_name),
			NotificationManager.IMPORTANCE_DEFAULT
		).apply {
			description = getString(R.string.channel_desc)
			setShowBadge(false)
		}
		notificationManager?.createNotificationChannel(channel)
	}
}