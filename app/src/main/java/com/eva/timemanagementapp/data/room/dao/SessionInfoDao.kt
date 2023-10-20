package com.eva.timemanagementapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity
import com.eva.timemanagementapp.domain.models.DurationOption
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

	//SELECT
	@Query(
		"""
		SELECT COUNT(*) FROM SESSION_INFO_TABLE S_INFO
		JOIN DAILY_SESSION_TABLE D_INFO 
		ON S_INFO.SESSION_ID = S_INFO.ID 
		AND D_INFO.DATE BETWEEN :from AND :to
 		AND S_INFO.TIMER_MODE =:mode
		"""
	)
	fun fetchSessionCountFromDateRange(from: LocalDate, to: LocalDate, mode: TimerModes): Flow<Int>

	@Query("SELECT COUNT(*) FROM SESSION_INFO_TABLE WHERE TIMER_MODE=:mode")
	fun fetchTotalSessions(mode: TimerModes): Flow<Int>

	@Query(
		"""
		SELECT SESSION_DURATION FROM SESSION_INFO_TABLE S_INFO 
		INNER JOIN DAILY_SESSION_TABLE D_INFO 
		ON S_INFO.SESSION_ID = D_INFO.ID 
		WHERE TIMER_MODE=:mode 
		AND DATE BETWEEN :from AND :to
	"""
	)
	fun fetchDurationsFromModeAndDateRange(
		from: LocalDate,
		to: LocalDate,
		mode: TimerModes
	): Flow<List<DurationOption>>

	@Query("SELECT SESSION_DURATION FROM SESSION_INFO_TABLE WHERE TIMER_MODE=:mode")
	fun fetchDurationsFromMode(mode: TimerModes): Flow<List<DurationOption>>


}