package com.eva.timemanagementapp.utils

enum class SessionServiceActions(val action: String) {
	START_TIMER("START_SESSION_STOPWATCH"),
	RESUME_TIMER("RESUME_SESSION_TIMER"),
	PAUSE_TIMER("PAUSE_SESSION_STOPWATCH"),
	STOP_TIMER("STOP_SESSION_STOPWATCH"),
}