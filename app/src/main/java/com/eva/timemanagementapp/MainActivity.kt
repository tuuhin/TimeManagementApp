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
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.eva.timemanagementapp.data.services.SessionService
import com.eva.timemanagementapp.presentation.composables.NoPermissionsFound
import com.eva.timemanagementapp.presentation.navigation.AppNavigationGraph
import com.eva.timemanagementapp.presentation.utils.LocalSnackBarProvider
import com.eva.timemanagementapp.presentation.utils.checkNotificationPermissions
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		val splash = installSplashScreen()
		super.onCreate(savedInstanceState)

		WindowCompat.setDecorFitsSystemWindows(window, false)

		lifecycleScope.launch {
			// Show the splash screen until the service is bound
			// When bound set keepOnScreenCondition to false
			hasServiceBounded
				.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
				.onEach { isDone -> splash.setKeepOnScreenCondition { !isDone } }
				.launchIn(this)
		}

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
								enter = fadeIn(tween(durationMillis = 600))
							) {
								AppNavigationGraph(service = timerService)
							}
							if (!isServiceBounded) Text(text = "CHECK")
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
