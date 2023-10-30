package com.eva.timemanagementapp.domain.stopwatch

import com.eva.timemanagementapp.utils.extensions.toMillisOfDay
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
import kotlin.time.Duration.Companion.seconds

/**
 * The Watch that presents the core functionality of the app ie A Timer
 * @property scope [CoroutineScope] in which the timer will run
 * @property initialTimerInMillis The initial state of the timer
 * @property interval The interval at which the timer updates.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TimerWatch(
	private val scope: CoroutineScope,
	private val initialTimerInMillis: Long = 1000L,
) {

	private val interval = 1.seconds

	private val _elapsedTime = MutableStateFlow(value = initialTimerInMillis)

	private val _states = MutableStateFlow(TimerWatchStates.IDLE)
	val state = _states.asStateFlow()

	private var timerJob: Job? = null

	/**
	 * A reverse counter stated from [initialTimerInMillis] and converted to [LocalTime] object
	 * until the [initialTimerInMillis] reached 0, then [LocalTime.MIN] is received by the subscriber.
	 */
	val elapsedTime = _elapsedTime.map { current ->
		if (current >= 0L) LocalTime.ofNanoOfDay(current * 1_000_000)
		else LocalTime.MIN
	}.stateIn(
		scope = scope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = LocalTime.MIN
	)

	init {
		try {
			timerJob = _states.flatMapLatest { state ->
				timeInMillsToSubtract(isRunning = state == TimerWatchStates.RUNNING)
			}.onEach { diff ->
				val current = _elapsedTime.updateAndGet { current ->
					(current - diff).coerceAtLeast(0L)
				}
				if (current <= 0L) {
					_states.update { TimerWatchStates.COMPLETED }
					return@onEach
				}
			}.launchIn(scope)
		} catch (e: Exception) {
			e.printStackTrace()
		}

	}

	/**
	 * Cancels the timerJob that is running.
	 * Cancelling the job is important as if the parent scope is cancelled.
	 * May lead to a cancellation Exception
	 */
	fun cancelTimerJob() = timerJob?.cancel()

	private fun timeInMillsToSubtract(isRunning: Boolean): Flow<Long> = flow {
		while (isRunning) {
			emit(interval.inWholeMilliseconds)
			delay(interval)
		}
	}

	/**
	 * Sets the [state] to [TimerWatchStates.PAUSED]
	 */
	fun onPause() = _states.update { TimerWatchStates.PAUSED }

	/**
	 * Sets the [state] to [TimerWatchStates.RUNNING]. This should be called if the [state] is
	 * [TimerWatchStates.PAUSED] otherwise it will have no effect
	 */
	fun onResume() = _states.update { TimerWatchStates.RUNNING }


	/**
	 * Reset's the watch ie, setting [elapsedTime] to 0L and [state] to [TimerWatchStates.IDLE]
	 */
	fun onReset() {
		_elapsedTime.update { 0L }
		_states.update { TimerWatchStates.IDLE }
	}


	/**
	 * Sets the [state] to [TimerWatchStates.IDLE]
	 */
	fun setModeIdle() = _states.update { TimerWatchStates.IDLE }


	/**
	 * Sets the time for the timer.
	 * @param minutes Minutes to be set in [Long]
	 */
	fun setTime(minutes: Int): Long = _elapsedTime.updateAndGet { minutes * 60 * 1000L }

	/**
	 * Sets the time for the timer.
	 * @param time the [LocalTime] to which it's to be set
	 */
	fun setTime(time: LocalTime) = _elapsedTime.updateAndGet { time.toMillisOfDay() }


	/**
	 * Starts the Watch by setting [state] to [TimerWatchStates.RUNNING]
	 */
	fun start() = _states.update { TimerWatchStates.RUNNING }

}