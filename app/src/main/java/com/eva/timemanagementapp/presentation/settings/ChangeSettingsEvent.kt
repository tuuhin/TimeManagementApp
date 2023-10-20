package com.eva.timemanagementapp.presentation.settings

import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption

sealed interface ChangeSettingsEvent {
	data class OnFocusDurationChange(val duration: DurationOption) : ChangeSettingsEvent
	data class OnBreakDurationChange(val duration: DurationOption) : ChangeSettingsEvent
	data class OnSessionCountChange(val number: SessionNumberOption) : ChangeSettingsEvent

	data class IsSaveSessionAllowed(val isAllowed: Boolean) : ChangeSettingsEvent
}