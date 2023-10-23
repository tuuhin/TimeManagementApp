package com.eva.timemanagementapp.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.TimerModes

@Entity(
	tableName = "SESSION_INFO_TABLE",
	foreignKeys = [
		ForeignKey(
			entity = DaySessionEntry::class,
			parentColumns = arrayOf("ID"),
			childColumns = arrayOf("SESSION_ID"),
			onDelete = ForeignKey.CASCADE,
			onUpdate = ForeignKey.NO_ACTION
		)
	],
)
data class SessionInfoEntity(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "ID")
	val id: Long? = null,

	@ColumnInfo(name = "SESSION_DURATION")
	val option: DurationOption,

	@ColumnInfo(name = "TIMER_MODE")
	val mode: TimerModes,

	@ColumnInfo(name = "SESSION_ID", index = true)
	val sessionId: Long,
)
