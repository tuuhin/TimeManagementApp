package com.eva.timemanagementapp.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AirplaneModeReceiver : BroadcastReceiver() {

	private var _listener: ((Boolean) -> Unit)? = null

	override fun onReceive(context: Context, intent: Intent) {

		val data = intent.getBooleanExtra("state", false)
		_listener?.invoke(data)
	}

	fun listenChanges(listener: ((Boolean) -> Unit)? = null) {
		_listener = listener
	}

}