package com.eva.timemanagementapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eva.timemanagementapp.data.room.entity.DaySessionEntry
import java.time.LocalDate

@Dao
interface DaySessionDao {

	// INSERT QUERIES
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertDayEntry(entity: DaySessionEntry): Long

	// SELECT QUERIES
	@Query("SELECT * FROM DAILY_SESSION_TABLE WHERE date = :date;")
	suspend fun fetchDaysEntryIfExists(date: LocalDate): DaySessionEntry?

	// DELETE QUERIES
	@Query("DELETE  FROM  DAILY_SESSION_TABLE WHERE DATE BETWEEN :from AND :to;")
	suspend fun removeDayEntryWithDateRange(from: LocalDate, to: LocalDate)

	@Query("DELETE FROM DAILY_SESSION_TABLE WHERE DATE=:date;")
	suspend fun removeDayEntryWithDate(date: LocalDate)

	@Delete
	suspend fun removeDailySessionEntry(entity: DaySessionEntry)

}