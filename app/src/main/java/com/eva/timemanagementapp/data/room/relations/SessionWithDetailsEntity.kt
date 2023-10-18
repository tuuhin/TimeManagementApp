package com.eva.timemanagementapp.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.eva.timemanagementapp.data.room.entity.DaySessionEntry
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity

data class SessionWithDetailsEntity(

	@Embedded
	val day: DaySessionEntry,

	@Relation(
		parentColumn = "ID",
		entityColumn = "SESSION_ID"
	)
	val sessions: List<SessionInfoEntity>
)