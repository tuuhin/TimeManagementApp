package com.eva.timemanagementapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeManagementApp : Application() {

	private val notificationManager by lazy { getSystemService<NotificationManager>() }
	override fun onCreate() {
		super.onCreate()

		val channelId = getString(R.string.channel_id)
		val channelName = getString(R.string.channel_name)
		val channelDescription = getString(R.string.channel_desc)

		val channel = NotificationChannel(
			channelId,
			channelName,
			NotificationManager.IMPORTANCE_HIGH
		).apply {
			description = channelDescription
			setShowBadge(false)
			lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
		}
		notificationManager?.createNotificationChannel(channel)
	}
}