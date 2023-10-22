package com.eva.timemanagementapp.domain.models

import java.time.LocalDate

data class SessionReportModel(
	val date: LocalDate,
	val sessionCount: Int,
)
