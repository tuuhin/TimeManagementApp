package com.eva.timemanagementapp.data.services

enum class RequestCodes(val code: Int) {
	START_ACTIVITY(100),
	START_SESSION_TIMER(101),
	PAUSE_SESSION_TIMER(102),
	STOP_SESSION_TIMER(103);
}