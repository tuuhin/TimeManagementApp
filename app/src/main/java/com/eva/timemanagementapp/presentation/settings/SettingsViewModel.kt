package com.eva.timemanagementapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.timemanagementapp.domain.facade.ServiceDataRetriever
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val settingsPreferences: SettingsPreferences,
	retriever: ServiceDataRetriever,
) : ViewModel() {

	val focusDuration = settingsPreferences.focusDuration
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(2000L),
			initialValue = DurationOption.TEN_MINUTES
		)

	val breakDuration = settingsPreferences.breakDuration
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(2000L),
			initialValue = DurationOption.TEN_MINUTES
		)

	val sessionCount = settingsPreferences.sessionCount.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = SessionNumberOption.TWO_TIMES
	)

	val isSaveSessionDataAllowed = settingsPreferences.isSaveSessions.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = false
	)

	val isAirPlaneModeEnabled = retriever.serviceStatus
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.Lazily,
			initialValue = retriever.initialValue
		)

	fun onChangeSettingsEvent(event: ChangeSettingsEvent) {
		when (event) {
			is ChangeSettingsEvent.OnFocusDurationChange -> viewModelScope.launch {
				settingsPreferences.setFocusDuration(event.duration)
			}

			is ChangeSettingsEvent.OnSessionCountChange -> viewModelScope.launch {
				settingsPreferences.setSessionCount(event.number)
			}

			is ChangeSettingsEvent.OnBreakDurationChange -> viewModelScope.launch {
				settingsPreferences.setBreakDuration(event.duration)
			}

			ChangeSettingsEvent.ToggleIsSaveSessionAllowed -> viewModelScope.launch {
				settingsPreferences.setIsSaveSessions(!isSaveSessionDataAllowed.value)
			}
		}
	}
}