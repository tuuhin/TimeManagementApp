package com.eva.timemanagementapp.presentation.settings

import com.eva.timemanagementapp.domain.models.SessionDurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption

sealed interface ChangeSettingsEvent {
	data class OnFocusDurationChange(val duration: SessionDurationOption) : ChangeSettingsEvent
	data class OnBreakDurationChange(val duration: SessionDurationOption) : ChangeSettingsEvent
	data class OnSessionCountChange(val number: SessionNumberOption) : ChangeSettingsEvent
}