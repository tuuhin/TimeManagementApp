package com.eva.timemanagementapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.StatisticsRepository
import com.eva.timemanagementapp.utils.extensions.toTwoDecimalFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class StatisticsRepoImpl(
	private val sessionDao: SessionInfoDao,
) : StatisticsRepository {


	override fun sessionAvgMinutes(
		mode: TimerModes,
		start: LocalDate?,
		end: LocalDate,
	): Flow<Float> = flow {
		try {
			val sessionFlow = start?.let {
				sessionDao.fetchDurationsFromModeAndDateRange(start = start, end = end, mode = mode)
			} ?: sessionDao.fetchDurationsFromMode(mode)

			sessionFlow.collect { options ->
				val totalMinutes = options.sumOf { it.minutes }
				val optionsCount = options.size.coerceAtLeast(1)

				val avg = (totalMinutes.toFloat() / optionsCount).toTwoDecimalFormat()
				emit(avg)
			}
		} catch (e: SQLiteConstraintException) {
			e.printStackTrace()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}.flowOn(Dispatchers.IO)


	override fun totalSessionCount(
		mode: TimerModes, start: LocalDate?, end: LocalDate
	): Flow<Int> = flow {
		try {
			val sessionCountFlow = start?.let {
				sessionDao.fetchSessionCountFromDateRange(start = start, end = end, mode = mode)
			} ?: sessionDao.fetchTotalSessions(mode = mode)

			sessionCountFlow.collect(::emit)
		} catch (e: SQLiteConstraintException) {
			e.printStackTrace()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}.flowOn(Dispatchers.IO)


	@OptIn(ExperimentalCoroutinesApi::class)
	override fun weeklyReport(
		mode: TimerModes, start: LocalDate, end: LocalDate
	): Flow<List<SessionReportModel>> = flow {
		try {
			val report =
				sessionDao
					.fetchMapOfDataAndSessionCount(start = start, end = end, mode = mode)
					.map { resource ->
						resource.map { entry ->
							SessionReportModel(date = entry.key, sessionCount = entry.value)
						}
					}.flatMapLatest(::weeklyReportFlatter)
			emitAll(report)
		} catch (e: SQLiteConstraintException) {
			e.printStackTrace()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}.flowOn(Dispatchers.IO)

	private fun weeklyReportFlatter(reports: List<SessionReportModel>) = flow {
		val extraDaysForReport = mutableListOf<SessionReportModel>()
		//Get the minimum of the report or if its null today only
		val minimum = reports.minOfOrNull { it.date } ?: LocalDate.now()

		var extrasRequired = (7 - reports.size).toLong()
		while (extrasRequired > 0) {
			val extra = (SessionReportModel(
				date = minimum.minusDays(extrasRequired),
				sessionCount = 0
			))
			extraDaysForReport.add(extra)
			extrasRequired--
		}
		val weekReport = extraDaysForReport + reports
		emit(weekReport)
	}
}
