package com.eva.timemanagementapp.domain.facade

import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import kotlinx.coroutines.flow.Flow

interface SettingsPreferences {
	val focusDuration: Flow<DurationOption>
	val breakDuration: Flow<DurationOption>
	val sessionCount: Flow<SessionNumberOption>

	val isSaveSessions: Flow<Boolean>

	suspend fun setFocusDuration(duration: DurationOption)

	suspend fun setBreakDuration(duration: DurationOption)

	suspend fun setSessionCount(count: SessionNumberOption)

	suspend fun setIsSaveSessions(allow: Boolean)
}