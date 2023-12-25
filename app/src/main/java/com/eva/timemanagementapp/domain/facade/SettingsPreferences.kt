package com.eva.timemanagementapp.domain.facade

import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime

interface SettingsPreferences {

	val focusDuration: Flow<DurationOption>

	val breakDuration: Flow<DurationOption>

	val sessionCount: Flow<SessionNumberOption>

	val isSaveSessions: Flow<Boolean>

	val reminderTime: Flow<LocalTime>

	val isGoalReminderActive: Flow<Boolean>

	suspend fun setFocusDuration(duration: DurationOption)

	suspend fun setBreakDuration(duration: DurationOption)

	suspend fun setSessionCount(count: SessionNumberOption)

	suspend fun setIsSaveSessions(allow: Boolean)

	suspend fun setReminderTime(time: LocalTime)

	suspend fun setIsGoalsReminderActiveness(isActive: Boolean)
}