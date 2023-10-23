package com.eva.timemanagementapp.presentation.statistics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.timemanagementapp.domain.models.SessionHighlightModel
import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.StatisticsRepository
import com.eva.timemanagementapp.presentation.utils.ShowContent
import com.eva.timemanagementapp.presentation.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
	private val repository: StatisticsRepository,
	private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

	private val tabIndexKey = "TAB_INDEX"

	private val _sessionHighLight = MutableStateFlow(SessionHighlightModel())
	val sessionHighLight = _sessionHighLight.asStateFlow()

	private val _graphMode = MutableStateFlow(TimerModes.FOCUS_MODE)
	val graphMode = _graphMode.asStateFlow()

	private val _uiEvents = MutableSharedFlow<UiEvents>()
	val uiEvents = _uiEvents.asSharedFlow()

	private val _weeklyGraph = MutableStateFlow(ShowContent<List<SessionReportModel>>())
	val weeklyGraph = _weeklyGraph.asStateFlow()

	val tabIndex = savedStateHandle.getStateFlow(key = tabIndexKey, initialValue = 0).map { index ->
		when (index) {
			0 -> StatisticsTabs.All
			1 -> StatisticsTabs.Weekly
			else -> StatisticsTabs.Today
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = StatisticsTabs.All
	)


	fun onTabIndexChanged(tab: StatisticsTabs) = savedStateHandle.set(tabIndexKey, tab.tabIndex)

	init {
		tabIndex
			.map(::evaluateStartDate)
			.onEach(::updateSessionHighLights)
			.launchIn(viewModelScope)

		// Loading the statistics graph
		_graphMode
			.onEach(::loadWeeklyReport)
			.launchIn(viewModelScope)
	}

	private fun evaluateStartDate(tab: StatisticsTabs): LocalDate? = when (tab) {
		StatisticsTabs.Today -> LocalDate.now()
		StatisticsTabs.Weekly -> LocalDate.now().minusDays(7)
		else -> null
	}

	private fun updateSessionHighLights(start: LocalDate? = null) {

		val tFocusFlow = repository.totalSessionCount(mode = TimerModes.FOCUS_MODE, start = start)
		val tBreakFlow = repository.totalSessionCount(mode = TimerModes.BREAK_MODE, start = start)

		val aFocusFlow = repository.sessionAvgMinutes(mode = TimerModes.FOCUS_MODE, start = start)
		val aBreakFlow = repository.sessionAvgMinutes(mode = TimerModes.BREAK_MODE, start = start)

		combine(
			tFocusFlow, tBreakFlow, aFocusFlow, aBreakFlow
		) { totalFocus, totalBreak, avgFocus, avgBreak ->
			_sessionHighLight.update {
				it.copy(
					totalFocusCount = totalFocus,
					totalBreakCount = totalBreak,
					avgFocus = avgFocus,
					avgBreak = avgBreak
				)
			}
		}.launchIn(viewModelScope)
	}

	fun onTimerModeChanged(mode: TimerModes) = _graphMode.update { mode }

	private fun loadWeeklyReport(mode: TimerModes) = repository.weeklyReport(
		mode = mode,
		start = LocalDate.now().minusDays(7)
	).onEach { report ->
		_weeklyGraph.update { state ->
			state.copy(isLoading = false, content = report)
		}
	}
		.launchIn(viewModelScope)


}