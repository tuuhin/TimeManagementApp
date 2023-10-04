package com.eva.timemanagementapp.presentation.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackBarProvider = compositionLocalOf { SnackbarHostState() }