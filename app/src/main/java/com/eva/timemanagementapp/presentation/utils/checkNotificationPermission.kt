package com.eva.timemanagementapp.presentation.utils

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

@Composable
fun checkNotificationPermissions(
	context: Context = LocalContext.current
): Boolean {
	var isPresent by remember {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) mutableStateOf(
			ContextCompat.checkSelfPermission(
				context, Manifest.permission.POST_NOTIFICATIONS
			) == PermissionChecker.PERMISSION_GRANTED
		)
		else mutableStateOf(true)
	}
	val permissionLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestPermission(),
		onResult = { isGranted -> isPresent = isGranted }
	)

	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPresent) {
		LaunchedEffect(Unit) {
			permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
		}
	}

	return isPresent
}