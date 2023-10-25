package com.eva.timemanagementapp.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import android.util.Log
import com.eva.timemanagementapp.data.receiver.AirplaneModeReceiver
import com.eva.timemanagementapp.domain.facade.SettingsInfoFacade
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

private const val ANDROID_TAG = "AIR_PLANE_MODE_RECEIVER"

class AirPlaneSettingsInfo(
	private val context: Context
) : SettingsInfoFacade {

	private val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
	private val settingsName = Settings.Global.AIRPLANE_MODE_ON

	private val receiver = AirplaneModeReceiver()
	override val initialValue: Boolean
		get() = Settings.Global.getInt(context.contentResolver, settingsName) == 1

	override val status: Flow<Boolean>
		get() = callbackFlow {
			Log.d(ANDROID_TAG, "RECEIVER_ADDED")
			context.registerReceiver(receiver, intentFilter)
			receiver.listenChanges(::trySend)
			awaitClose {
				Log.d(ANDROID_TAG, "RECEIVER_REMOVED")
				context.unregisterReceiver(receiver)
			}
		}
}