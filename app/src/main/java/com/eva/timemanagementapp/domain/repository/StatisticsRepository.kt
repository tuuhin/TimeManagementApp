package com.eva.timemanagementapp.domain.repository

import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface StatisticsRepository {


	fun sessionAvgMinutes(
		mode: TimerModes,
		start: LocalDate?,
		end: LocalDate = LocalDate.now()
	): Flow<Float>

	fun totalSessionCount(
		mode: TimerModes,
		start: LocalDate?,
		end: LocalDate = LocalDate.now()
	): Flow<Int>

	fun weeklyReport(
		mode: TimerModes,
		start: LocalDate,
		end: LocalDate = LocalDate.now()
	): Flow<List<SessionReportModel>>

	suspend fun deleteStatisticsData(
		start: LocalDate?,
		end: LocalDate = LocalDate.now()
	): Resource<Unit>

}