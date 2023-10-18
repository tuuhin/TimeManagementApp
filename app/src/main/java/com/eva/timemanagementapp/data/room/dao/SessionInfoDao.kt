package com.eva.timemanagementapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity
import com.eva.timemanagementapp.data.room.relations.SessionWithDetailsEntity
import com.eva.timemanagementapp.domain.models.TimerModes
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface SessionInfoDao {

	// INSERT
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertSessionEntry(entity: SessionInfoEntity)

	// DELETE
	@Delete
	suspend fun deleteSessionEntity(entity: SessionInfoEntity)

	@Query(
		"""
		SELECT COUNT(*) FROM SESSION_INFO_TABLE S_INFO
		JOIN DAILY_SESSION_TABLE D_INFO 
		ON S_INFO.SESSION_ID = S_INFO.ID 
		AND D_INFO.DATE BETWEEN :from AND :to
 		AND S_INFO.TIMER_MODE =:mode
		"""
	)
	fun fetchSessionWithDetailsCount(from: LocalDate, to: LocalDate, mode: TimerModes): Flow<Int>

	@Query(
		"""
		SELECT AVG(*) FROM SESSION_INFO_TABLE
		JOIN DAILY_SESSION_TABLE 
		ON SESSION_INFO_TABLE.SESSION_ID = DAILY_SESSION_TABLE.ID
		AND TIMER_MODE=:mode
	"""
	)
	fun fetchDailyAverage(mode: TimerModes): Flow<Int>


	@Query("SELECT COUNT(*) FROM SESSION_INFO_TABLE WHERE TIMER_MODE=:mode")
	fun fetchTotalSessions(mode: TimerModes): Flow<Int>

	@Transaction
	@Query(
		"""
		SELECT * FROM SESSION_INFO_TABLE 
		LEFT JOIN DAILY_SESSION_TABLE 
		ON SESSION_INFO_TABLE.SESSION_ID == DAILY_SESSION_TABLE.ID 
		AND DAILY_SESSION_TABLE.DATE=:date;
		"""
	)
	fun fetchSessionWithDate(date: LocalDate): Flow<SessionWithDetailsEntity>

}