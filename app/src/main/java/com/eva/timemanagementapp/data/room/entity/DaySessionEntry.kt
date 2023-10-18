package com.eva.timemanagementapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
	tableName = "DAILY_SESSION_TABLE",
)
data class DaySessionEntry(

	@ColumnInfo(name = "ID")
	@PrimaryKey(autoGenerate = true)
	val id: Long? = null,

	@ColumnInfo(name = "DATE")
	val date: LocalDate,
)
