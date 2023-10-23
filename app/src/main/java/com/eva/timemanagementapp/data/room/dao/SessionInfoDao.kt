package com.eva.timemanagementapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
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
		INNER JOIN DAILY_SESSION_TABLE D_INFO 
		ON S_INFO.SESSION_ID = D_INFO.ID 
 		WHERE S_INFO.TIMER_MODE =:mode
		AND D_INFO.DATE BETWEEN :start AND :end
		"""
	)
	fun fetchSessionCountFromDateRange(
		start: LocalDate,
		end: LocalDate,
		mode: TimerModes
	): Flow<Int>

	@Query("SELECT COUNT(*) FROM SESSION_INFO_TABLE WHERE TIMER_MODE=:mode")
	fun fetchTotalSessions(mode: TimerModes): Flow<Int>

	@Query(
		"""
		SELECT SESSION_DURATION FROM SESSION_INFO_TABLE S_INFO 
		INNER JOIN DAILY_SESSION_TABLE D_INFO 
		ON S_INFO.SESSION_ID = D_INFO.ID 
		WHERE TIMER_MODE=:mode 
		AND DATE BETWEEN :start AND :end 
	"""
	)
	fun fetchDurationsFromModeAndDateRange(
		start: LocalDate,
		end: LocalDate,
		mode: TimerModes
	): Flow<List<DurationOption>>

	@Query("SELECT SESSION_DURATION FROM SESSION_INFO_TABLE WHERE TIMER_MODE=:mode")
	fun fetchDurationsFromMode(mode: TimerModes): Flow<List<DurationOption>>

	@MapInfo(keyColumn = "DATE", valueColumn = "S_COUNT")
	@Query(
		"""
		SELECT DATE,COUNT(*) as S_COUNT 
		FROM SESSION_INFO_TABLE S_INFO 
		INNER JOIN DAILY_SESSION_TABLE D_INFO 
		ON S_INFO.SESSION_ID = D_INFO.ID 
 		WHERE S_INFO.TIMER_MODE =:mode
		AND D_INFO.DATE BETWEEN :start AND :end
		GROUP BY DATE
	    """
	)
	fun fetchMapOfDataAndSessionCount(
		start: LocalDate,
		end: LocalDate,
		mode: TimerModes
	): Flow<Map<LocalDate, Int>>


}