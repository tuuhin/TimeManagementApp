package com.eva.timemanagementapp.presentation.timer

sealed interface TimerEvents {
	data object OnFocusModeStart : TimerEvents
	data object OnBreakModeStart : TimerEvents
	data object OnPause : TimerEvents
	data object OnResume : TimerEvents
	data object OnStopSession : TimerEvents
}