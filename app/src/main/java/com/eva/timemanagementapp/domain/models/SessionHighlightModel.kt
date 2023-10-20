package com.eva.timemanagementapp.domain.models

data class SessionHighlightModel(
	val totalBreakCount: Int = 0,
	val totalFocusCount: Int = 0,
	val avgFocus: Float = 1f,
	val avgBreak: Float = 1f,
)
