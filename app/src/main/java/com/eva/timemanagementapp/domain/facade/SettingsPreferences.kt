package com.eva.timemanagementapp.domain.facade

import com.eva.timemanagementapp.domain.models.SessionDurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import kotlinx.coroutines.flow.Flow

interface SettingsPreferences {
	val focusDuration: Flow<SessionDurationOption>
	val breakDuration: Flow<SessionDurationOption>
	val sessionCount: Flow<SessionNumberOption>

	suspend fun setFocusDuration(duration: SessionDurationOption)

	suspend fun setBreakDuration(duration: SessionDurationOption)

	suspend fun setSessionCount(count: SessionNumberOption)
}