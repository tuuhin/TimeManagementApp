package com.eva.timemanagementapp.domain.stopwatch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class StopWatch(
	private val scope: CoroutineScope,
) {
	private val initialState = LocalTime.MIN

	private val _elapsedTime = MutableStateFlow(0L)
	private val _states = MutableStateFlow(StopWatchStates.PAUSED)

	val state = _states.asStateFlow()


	val elapsedTime = _elapsedTime
		.map { LocalTime.ofNanoOfDay(it * 1_000_000) }
		.stateIn(
			scope = scope,
			started = SharingStarted.Lazily,
			initialValue = initialState
		)

	init {
		_states.flatMapLatest { state ->
			getCurrentTimeInMills(state == StopWatchStates.RUNNING)
		}.onEach { diff ->
			_elapsedTime.update { it + diff }
		}.launchIn(scope)
	}

	private fun getCurrentTimeInMills(isRunning: Boolean, interval: Long = 10L): Flow<Long> = flow {
		var timeOld = System.currentTimeMillis()
		while (isRunning) {
			delay(interval)
			val timeNow = System.currentTimeMillis()
			val diff = if (timeNow > timeOld) timeNow - timeOld else 0L
			emit(diff)
			timeOld = System.currentTimeMillis()
		}
	}.flowOn(scope.coroutineContext)

	fun onPause() = _states.update { StopWatchStates.PAUSED }

	fun onRest() {
		_elapsedTime.update { 0L }
		_states.update { StopWatchStates.PAUSED }
	}

	fun start() = _states.update { StopWatchStates.RUNNING }
}