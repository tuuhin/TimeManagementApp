package com.eva.timemanagementapp.domain.stopwatch

import app.cash.turbine.turbineScope
import com.eva.timemanagementapp.utils.MainDispatcherRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import java.time.LocalTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalCoroutinesApi::class)
class TimerWatchTest {

	@get:Rule
	val testDispatcher = MainDispatcherRule()

	private lateinit var scope: CoroutineScope
	private lateinit var timerWatch: TimerWatch


	@BeforeTest
	fun setUp() {
		scope = CoroutineScope(Dispatchers.Main)
		timerWatch = TimerWatch(scope = scope)
	}

	@AfterTest
	fun tearDown() {
		timerWatch.cancelTimerJob()
		scope.cancel()
	}

	@Test
	fun `checking the initial state of the clock`() = runTest {
		turbineScope(timeout = 12.seconds) {
			val elapsedTimeTest = timerWatch.elapsedTime.testIn(this)
			val watchStateTest = timerWatch.state.testIn(this)

			assertEquals(
				TimerWatchStates.IDLE,
				watchStateTest.awaitItem(),
				message = "The initial state for the watch should be idle"
			)

			assertEquals(
				LocalTime.MIN,
				elapsedTimeTest.awaitItem(),
				message = "The timer state is too null"
			)

			watchStateTest.cancelAndIgnoreRemainingEvents()
			elapsedTimeTest.cancelAndIgnoreRemainingEvents()

		}
	}

	@Test
	fun `checking the clock running mechanism`() = runTest {
		turbineScope(timeout = 12.seconds) {
			val elapsedTimeTest = timerWatch.elapsedTime.testIn(this)
			val watchStateTest = timerWatch.state.testIn(this)

			timerWatch.setTime(1)
			runCurrent()

			assertEquals(
				LocalTime.of(0, 1, 0),
				elapsedTimeTest.expectMostRecentItem(),
				message = "Current timer time is set to 1 minutes "
			)

			timerWatch.start()

			assertEquals(
				TimerWatchStates.RUNNING,
				watchStateTest.expectMostRecentItem(),
				message = "The timer is running"
			)
			// advance the timer by 5 seconds
			advanceTimeBy(5.seconds)

			timerWatch.onPause()
			runCurrent()

			assertEquals(
				TimerWatchStates.PAUSED,
				watchStateTest.expectMostRecentItem(),
				message = "The timer is running"
			)

			assertEquals(
				LocalTime.of(0, 0, 55),
				elapsedTimeTest.expectMostRecentItem(),
				message = "The timer passed 5 seconds"
			)

			watchStateTest.cancelAndIgnoreRemainingEvents()
			elapsedTimeTest.cancelAndIgnoreRemainingEvents()

		}
	}

	@Test
	fun `check if the reset works`() = runTest {
		turbineScope {
			val elapsedTimeTest = timerWatch.elapsedTime.testIn(this)
			val watchStateTest = timerWatch.state.testIn(this)

			timerWatch.setTime(1)
			runCurrent()

			assertEquals(
				LocalTime.of(0, 1, 0),
				elapsedTimeTest.expectMostRecentItem(),
				message = "Current timer time is set to 1 minutes "
			)

			timerWatch.start()
			advanceTimeBy(5.seconds)

			assertEquals(
				TimerWatchStates.RUNNING,
				watchStateTest.expectMostRecentItem(),
				message = "The timer is running"
			)


			timerWatch.onReset()
			runCurrent()

			assertEquals(
				LocalTime.of(0, 0, 0),
				elapsedTimeTest.expectMostRecentItem(),
				message = "Elapsed time should show 00:00:00"
			)

			assertEquals(
				TimerWatchStates.IDLE,
				watchStateTest.expectMostRecentItem(),
				message = "Time state after reset"
			)


			watchStateTest.cancelAndIgnoreRemainingEvents()
			elapsedTimeTest.cancelAndIgnoreRemainingEvents()
		}
	}

	@Test
	fun `is the timer is showing completed state on complete`() = runTest {
		turbineScope {

			val elapsedTimeTest = timerWatch.elapsedTime.testIn(this)
			val watchStateTest = timerWatch.state.testIn(this)

			timerWatch.setTime(LocalTime.of(0, 0, 10))
			runCurrent()

			assertEquals(
				LocalTime.of(0, 0, 10),
				elapsedTimeTest.expectMostRecentItem(),
				message = "Current Timer is set to 10 seconds"
			)

			timerWatch.start()
			advanceTimeBy(5.seconds)

			assertEquals(
				TimerWatchStates.RUNNING,
				watchStateTest.expectMostRecentItem(),
				message = "The timer is running"
			)

			advanceTimeBy(3.seconds)

			assertNotEquals(
				LocalTime.MIN,
				elapsedTimeTest.expectMostRecentItem(),
				message = "Current timer after 5+3 seconds "
			)

			// After this final 2 seconds delay it should stop
			advanceTimeBy(2.seconds)

			assertEquals(
				LocalTime.MIN,
				elapsedTimeTest.expectMostRecentItem(),
				message = "Current Timer after 5+3+2 seconds"
			)

			assertEquals(
				TimerWatchStates.COMPLETED,
				watchStateTest.expectMostRecentItem(),
				message = "The timer is completed"
			)

			timerWatch.onReset()
			runCurrent()

			assertEquals(
				TimerWatchStates.IDLE,
				watchStateTest.expectMostRecentItem(),
				message = "The timer is again set to idle state"
			)

			watchStateTest.cancelAndIgnoreRemainingEvents()
			elapsedTimeTest.cancelAndIgnoreRemainingEvents()
		}
	}

}