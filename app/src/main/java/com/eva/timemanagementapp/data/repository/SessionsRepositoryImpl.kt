package com.eva.timemanagementapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.eva.timemanagementapp.data.room.dao.DaySessionDao
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.data.room.entity.DaySessionEntry
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity
import com.eva.timemanagementapp.data.room.mapper.toModels
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionData
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.SessionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class SessionsRepositoryImpl(
	private val dailySessionDao: DaySessionDao,
	private val sessionDao: SessionInfoDao,
) : SessionsRepository {
	override suspend fun addTimerSession(
		option: DurationOption,
		mode: TimerModes
	): Boolean = withContext(Dispatchers.IO) {
		try {
			val today = LocalDate.now()
			val entity = dailySessionDao.fetchDaysEntryIfExists(today)
			val entityId = entity?.id ?: kotlin.run {
				val newEntity = DaySessionEntry(date = today)
				dailySessionDao.insertDayEntry(newEntity)
			}

			val session = SessionInfoEntity(
				option = option,
				mode = mode,
				at = LocalTime.now(),
				sessionId = entityId
			)
			sessionDao.insertSessionEntry(session)
			true
		} catch (e: SQLiteConstraintException) {
			e.printStackTrace()
			false
		} catch (e: Exception) {
			e.printStackTrace()
			false
		}
	}

	override suspend fun sessionInfoFlow(): Flow<List<SessionData>> = withContext(Dispatchers.IO) {
		sessionDao.fetchSessionWithDate(LocalDate.now()).map { it.toModels() }
	}
}