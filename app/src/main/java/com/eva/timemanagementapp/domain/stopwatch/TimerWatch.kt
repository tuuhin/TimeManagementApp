package com.eva.timemanagementapp.domain.stopwatch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class TimerWatch(
	scope: CoroutineScope,
	initialTimerInMillis: Long = 1000L,
	private val interval: Long = 10L,
) {

	private val _elapsedTime = MutableStateFlow(value = initialTimerInMillis)

	private val _states = MutableStateFlow(TimerWatchStates.IDLE)
	val state = _states.asStateFlow()

	private var timerJob: Job? = null

	val elapsedTime = _elapsedTime.map { current ->
		if (current > 0L) LocalTime.ofNanoOfDay(current * 1_000_000)
		else LocalTime.MIN
	}.stateIn(
		scope = scope,
		started = SharingStarted.Lazily,
		initialValue = LocalTime.ofSecondOfDay(initialTimerInMillis)
	)

	init {
		timerJob = _states.flatMapLatest { state ->
			getCurrentTimeInMills(isRunning = state == TimerWatchStates.RUNNING)
		}.onEach { diff ->
			val current = _elapsedTime.updateAndGet { current -> current - diff }
			if (current < 0L) {
				_states.update { TimerWatchStates.COMPLETED }
			}
		}.launchIn(scope)

	}

	fun cancelTimerJob() = timerJob?.cancel()

	private fun getCurrentTimeInMills(isRunning: Boolean): Flow<Long> = flow {
		var timeOld = System.currentTimeMillis()
		while (isRunning) {
			delay(interval)
			val timeNow = System.currentTimeMillis()
			val diff = if (timeNow > timeOld) timeNow - timeOld else 0L
			emit(diff)
			timeOld = System.currentTimeMillis()
		}
	}

	fun onPause() = _states.update { TimerWatchStates.PAUSED }

	fun onResume() = _states.update { TimerWatchStates.RUNNING }

	fun onReset() {
		_elapsedTime.update { 0L }
		_states.update { TimerWatchStates.IDLE }
	}

	fun setModeIdle() = _states.update { TimerWatchStates.IDLE }

	fun setTime(minutes: Int) {
		val time = LocalTime.of(0, minutes, 0)
		val milliSeconds = time.toSecondOfDay() * 1000L
		_elapsedTime.update { milliSeconds }

	}

	fun start() = _states.update { TimerWatchStates.RUNNING }

}