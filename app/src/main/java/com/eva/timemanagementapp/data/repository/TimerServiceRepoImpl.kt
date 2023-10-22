package com.eva.timemanagementapp.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.eva.timemanagementapp.data.room.dao.DaySessionDao
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.data.room.entity.DaySessionEntry
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.TimerServiceRepository
import com.eva.timemanagementapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class TimerServiceRepoImpl(
	private val dailySessionDao: DaySessionDao,
	private val sessionDao: SessionInfoDao,
) : TimerServiceRepository {

	override suspend fun addTimerSession(
		sessionDate: LocalDate,
		option: DurationOption,
		mode: TimerModes
	): Resource<Boolean> = withContext(Dispatchers.IO) {
		try {
			val entity = dailySessionDao.fetchDaysEntryIfExists(sessionDate)
			val sessionId = entity?.id ?: kotlin.run {
				val newEntity = DaySessionEntry(date = sessionDate)
				dailySessionDao.insertDayEntry(newEntity)
			}
			val session = SessionInfoEntity(
				option = option,
				mode = mode,
				at = LocalTime.now(),
				sessionId = sessionId
			)
			sessionDao.insertSessionEntry(session)
			Resource.Success(true)
		} catch (e: SQLiteConstraintException) {
			e.printStackTrace()
			Resource.Error(errorMessage = e.message ?: "")
		} catch (e: Exception) {
			e.printStackTrace()
			Resource.Error(errorMessage = e.message ?: "")
		}
	}

}