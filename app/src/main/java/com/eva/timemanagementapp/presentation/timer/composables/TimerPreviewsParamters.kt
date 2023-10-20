package com.eva.timemanagementapp.presentation.timer.composables

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates

class TimerModesPreviewParams : CollectionPreviewParameterProvider<TimerModes>(
	collection = TimerModes.entries.toList()
)

class TimerPlayPausePreviewParams : CollectionPreviewParameterProvider<TimerWatchStates>(
	listOf(TimerWatchStates.PAUSED, TimerWatchStates.RUNNING)
)