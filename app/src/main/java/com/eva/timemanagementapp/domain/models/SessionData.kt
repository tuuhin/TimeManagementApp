package com.eva.timemanagementapp.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class SessionData(
	val id: Int? = null,
	val session: DurationOption,
	val at: LocalTime,
	val mode: TimerModes,
	val date: LocalDate,
)
