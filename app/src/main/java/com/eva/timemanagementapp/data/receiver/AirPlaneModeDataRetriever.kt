package com.eva.timemanagementapp.data.receiver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import android.util.Log
import com.eva.timemanagementapp.domain.facade.ServiceDataRetriever
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AirPlaneModeDataRetriever(
	private val context: Context
) : ServiceDataRetriever {

	private val intent = Intent.ACTION_AIRPLANE_MODE_CHANGED
	private val settingsName = Settings.Global.AIRPLANE_MODE_ON

	private val receiver = AirplaneModeReceiver()
	override val initialValue: Boolean
		get() = Settings.Global.getInt(context.contentResolver, settingsName) == 1

	override val serviceStatus: Flow<Boolean>
		get() = callbackFlow {
			context.registerReceiver(receiver, IntentFilter(intent))
			receiver.listenChanges { trySend(it) }
			awaitClose {
				Log.d("SOMETHING", "REMOVED")
				context.unregisterReceiver(receiver)
			}
		}
}