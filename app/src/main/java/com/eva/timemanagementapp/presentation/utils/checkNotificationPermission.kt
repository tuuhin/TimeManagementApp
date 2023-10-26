package com.eva.timemanagementapp.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.eva.timemanagementapp.presentation.composables.CheckPermissionsDialog

@Composable
fun checkNotificationPermissions(
	context: Context = LocalContext.current
): Pair<Boolean, () -> Unit> {

	var isPresent by remember {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
			mutableStateOf(
				ContextCompat.checkSelfPermission(
					context, Manifest.permission.POST_NOTIFICATIONS
				) == PermissionChecker.PERMISSION_GRANTED
			)
		else mutableStateOf(true)
	}

	var showPermissionDialog by remember { mutableStateOf(false) }

	if (showPermissionDialog) {
		CheckPermissionsDialog(
			onDismiss = { showPermissionDialog = false },
			onShowSettings = {
				Intent(
					Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
					Uri.fromParts("package", context.packageName, null)
				).apply(context::startActivity)

				showPermissionDialog = false
			},
		)
	}

	val permissionLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestPermission(),
		onResult = { isGranted -> isPresent = isGranted }
	)

	val requestPermissions = {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPresent) {

			val isPermanentlyDeclined = (context as Activity)
				.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)

			when {
				ContextCompat.checkSelfPermission(
					context, Manifest.permission.POST_NOTIFICATIONS
				) == PermissionChecker.PERMISSION_GRANTED -> isPresent = true

				isPermanentlyDeclined -> showPermissionDialog = true

				else -> permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
			}
		}
	}

	LaunchedEffect(Unit, block = { requestPermissions() })


	return Pair(isPresent, requestPermissions)
}
