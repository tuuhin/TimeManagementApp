package com.eva.timemanagementapp.data.room.mapper

import com.eva.timemanagementapp.data.room.relations.SessionWithDetailsEntity
import com.eva.timemanagementapp.domain.models.SessionData

fun SessionWithDetailsEntity.toModels(): List<SessionData> = sessions.map {
	SessionData(
		id = it.id?.toInt(),
		session = it.option,
		at = it.at,
		mode = it.mode,
		date = day.date
	)
}
