package com.eva.timemanagementapp.utils

enum class RequestCodes(val code: Int) {
	START_ACTIVITY(100),
	START_TIMER(101),
	PAUSE_TIMER(102),
	RESUME_TIMER(103),
	STOP_TIMER(104),
	DISMISS_TIMER(105);
}