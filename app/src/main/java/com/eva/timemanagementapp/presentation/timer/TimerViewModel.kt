package com.eva.timemanagementapp.presentation.timer

import androidx.lifecycle.ViewModel
import com.eva.timemanagementapp.data.services.UIServiceController
import com.eva.timemanagementapp.domain.models.TimerModes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
	private val serviceHelper: UIServiceController
) : ViewModel() {

	fun onTimerEvents(event: TimerEvents) = when (event) {
		TimerEvents.OnPause -> serviceHelper.pauseTimer()
		TimerEvents.OnResume -> serviceHelper.resumeTimer()
		TimerEvents.OnFocusModeStart -> serviceHelper.startTimer(TimerModes.FOCUS_MODE)
		TimerEvents.OnBreakModeStart -> serviceHelper.startTimer(TimerModes.BREAK_MODE)
		TimerEvents.OnStopSession -> serviceHelper.stopTimer()
	}

}