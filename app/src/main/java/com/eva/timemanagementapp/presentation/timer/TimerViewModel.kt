package com.eva.timemanagementapp.presentation.timer

import androidx.lifecycle.ViewModel
import com.eva.timemanagementapp.data.services.UIServiceController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
	private val serviceHelper: UIServiceController
) : ViewModel() {

	private val _testDuration = LocalTime.of(0, 5, 1)

	private val _timerDuration = MutableStateFlow(LocalTime.of(0, 0, 1))
	val timerDuration = _timerDuration.asStateFlow()

	fun onTimerEvents(event: TimerEvents) {
		when (event) {
			TimerEvents.OnPause -> serviceHelper.pauseTimer()
			TimerEvents.OnResume -> serviceHelper.resumeTimer()
			is TimerEvents.OnStart -> {
				_timerDuration.update { _testDuration }
				serviceHelper.startTimer(_testDuration.minute)
			}
		}
	}
}