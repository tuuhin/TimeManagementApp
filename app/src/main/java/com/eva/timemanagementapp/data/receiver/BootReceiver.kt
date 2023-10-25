package com.eva.timemanagementapp.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.eva.timemanagementapp.domain.facade.SessionReminderFacade
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val ANDROID_LOGGER = "BOOT_RECEIVER"

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

	@Inject
	lateinit var sessionReminder: SessionReminderFacade

	@Inject
	lateinit var preferences: SettingsPreferences

	private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

	override fun onReceive(context: Context, intent: Intent) {
		if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

		scope.launch {
			try {
				val reminderTime = preferences.reminderTime.first()
				sessionReminder.setGoalReminderAlarm(reminderTime)
				Log.d(ANDROID_LOGGER, "DONE_EXECUTING")
			} catch (e: Exception) {
				Log.d(ANDROID_LOGGER, e.message ?: "SOME EXCEPTION OCCCURED")
				e.printStackTrace()
			} finally {
				Log.d(ANDROID_LOGGER, "CANCELLING SCOPE")
				scope.cancel()
			}
		}
	}
}