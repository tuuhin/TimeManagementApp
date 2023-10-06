package com.eva.timemanagementapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.eva.timemanagementapp.data.services.SessionService
import com.eva.timemanagementapp.presentation.utils.LocalSnackBarProvider
import com.eva.timemanagementapp.presentation.utils.checkNotificationPermissions
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	private lateinit var _sessionService: SessionService
	private var isBound = false

	private val serviceConnection = object : ServiceConnection {
		override fun onServiceConnected(name: ComponentName, service: IBinder) {
			val binder = service as SessionService.SessionServiceBinder
			_sessionService = binder.service
			isBound = true
		}

		override fun onServiceDisconnected(name: ComponentName) {
			isBound = false
		}
	}

	override fun onStart() {
		super.onStart()
		Intent(this, SessionService::class.java).apply {
			bindService(this@apply, serviceConnection, Context.BIND_AUTO_CREATE)
		}
	}

	override fun onStop() {
		unbindService(serviceConnection)
		super.onStop()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			TimeManagementAppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val notificationPerms = checkNotificationPermissions()
					val snackBarHostState = remember { SnackbarHostState() }
					CompositionLocalProvider(
						LocalSnackBarProvider provides snackBarHostState
					) {
					}
				}
			}
		}
	}
}
