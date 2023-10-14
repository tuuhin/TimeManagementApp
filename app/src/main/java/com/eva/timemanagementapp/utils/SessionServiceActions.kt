package com.eva.timemanagementapp.utils

enum class SessionServiceActions(val action: String) {
	START_TIMER("START_SESSION_TIMER"),
	RESUME_TIMER("RESUME_SESSION_TIMER"),
	PAUSE_TIMER("PAUSE_SESSION_TIMER"),
	STOP_TIMER("STOP_SESSION_TIMER"),
	STOP_SESSION("STOP_SESSION")
}