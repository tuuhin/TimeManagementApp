package com.eva.timemanagementapp.domain.repository

import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionData
import com.eva.timemanagementapp.domain.models.TimerModes
import kotlinx.coroutines.flow.Flow

interface SessionsRepository {

	suspend fun addTimerSession(option: DurationOption, mode: TimerModes): Boolean

	suspend fun sessionInfoFlow(): Flow<List<SessionData>>
}