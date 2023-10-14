package com.eva.timemanagementapp.domain.models

enum class TimerModes {
	FOCUS_MODE,
	BREAK_MODE;

	companion object {
		fun switchModes(mode: TimerModes) = when (mode) {
			FOCUS_MODE -> BREAK_MODE
			BREAK_MODE -> FOCUS_MODE
		}
	}
}