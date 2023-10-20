package com.eva.timemanagementapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.StatisticsRepository
import com.eva.timemanagementapp.utils.extensions.toTwoDecimalFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate

class StatisticsRepoImpl(
	private val sessionDao: SessionInfoDao,
) : StatisticsRepository {

	override fun sessionAvgMinutes(
		mode: TimerModes, start: LocalDate?, end: LocalDate,
	): Flow<Float> = flow {
		try {
			(start?.let { sessionDao.fetchDurationsFromModeAndDateRange(start, end, mode) }
				?: sessionDao.fetchDurationsFromMode(mode))
				.collect { options ->
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

	override fun totalSessionCount(mode: TimerModes, start: LocalDate?, end: LocalDate): Flow<Int> =
		flow {
			try {
				(start?.let {
					sessionDao.fetchSessionCountFromDateRange(from = start, to = end, mode = mode)
				} ?: sessionDao.fetchTotalSessions(mode = mode)).collect(::emit)
			} catch (e: SQLiteConstraintException) {
				e.printStackTrace()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}.flowOn(Dispatchers.IO)
}
