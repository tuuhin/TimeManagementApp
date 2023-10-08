package com.eva.timemanagementapp.presentation.timer

sealed interface TimerEvents {
	data object OnStart : TimerEvents
	data object OnPause : TimerEvents
	data object OnResume : TimerEvents
}