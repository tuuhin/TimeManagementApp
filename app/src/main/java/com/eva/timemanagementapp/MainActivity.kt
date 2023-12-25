package com.eva.timemanagementapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eva.timemanagementapp.data.services.SessionService
import com.eva.timemanagementapp.presentation.composables.NoPermissionsFound
import com.eva.timemanagementapp.presentation.navigation.AppNavigationGraph
import com.eva.timemanagementapp.presentation.utils.LocalSnackBarProvider
import com.eva.timemanagementapp.presentation.utils.checkNotificationPermissions
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	private lateinit var timerService: SessionService

	private val hasServiceBounded = MutableStateFlow(false)

	private val connection = object : ServiceConnection {
		override fun onServiceConnected(name: ComponentName, service: IBinder) {
			val binder = service as SessionService.ServiceBinder
			timerService = binder.service
			hasServiceBounded.update { true }
		}

		override fun onServiceDisconnected(name: ComponentName) {
			hasServiceBounded.update { false }
		}
	}

	override fun onStart() {
		super.onStart()
		Intent(this, SessionService::class.java).apply {
			bindService(this, connection, Context.BIND_AUTO_CREATE)
		}
	}


	override fun onStop() {
		super.onStop()
		unbindService(connection)
		hasServiceBounded.update { false }
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		val splash = installSplashScreen()
		super.onCreate(savedInstanceState)

		WindowCompat.setDecorFitsSystemWindows(window, false)
		splash.setKeepOnScreenCondition { hasServiceBounded.value }

		setContent {
			TimeManagementAppTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background,
				) {
					val snackBarHostState = remember { SnackbarHostState() }
					val (isGranted, callback) = checkNotificationPermissions()

					val isServiceBounded by hasServiceBounded.collectAsStateWithLifecycle()

					when {
						isGranted -> CompositionLocalProvider(
							LocalSnackBarProvider provides snackBarHostState,
						) {
							AnimatedVisibility(
								visible = isServiceBounded,
								modifier = Modifier.fillMaxSize(),
								enter = fadeIn(),
								exit = fadeOut()
							) {
								AppNavigationGraph(service = timerService)
							}
						}

						else -> {
							Box(
								modifier = Modifier.fillMaxSize(),
								contentAlignment = Alignment.Center,
								content = {
									NoPermissionsFound(checkPerms = callback)
								},
							)
						}
					}
				}
			}
		}
	}
}
