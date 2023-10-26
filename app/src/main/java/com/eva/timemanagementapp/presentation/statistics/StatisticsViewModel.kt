package com.eva.timemanagementapp.presentation.statistics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eva.timemanagementapp.domain.models.SessionHighlightModel
import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.repository.StatisticsRepository
import com.eva.timemanagementapp.presentation.statistics.utils.DeleteEvents
import com.eva.timemanagementapp.presentation.statistics.utils.DeleteStatisticsState
import com.eva.timemanagementapp.presentation.statistics.utils.StatisticsType
import com.eva.timemanagementapp.presentation.utils.ShowContent
import com.eva.timemanagementapp.presentation.utils.UiEvents
import com.eva.timemanagementapp.utils.Resource
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
import kotlinx.coroutines.launch
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

	private val _deleteState = MutableStateFlow(DeleteStatisticsState())
	val deleteState = _deleteState.asStateFlow()

	val tabIndex = savedStateHandle.getStateFlow(key = tabIndexKey, initialValue = 0).map { index ->
		when (index) {
			0 -> StatisticsType.All
			1 -> StatisticsType.Weekly
			else -> StatisticsType.Today
		}
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(2000L),
		initialValue = StatisticsType.Weekly
	)


	fun onTabIndexChanged(tab: StatisticsType) = savedStateHandle.set(tabIndexKey, tab.tabIndex)

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

	private fun evaluateStartDate(tab: StatisticsType): LocalDate? = when (tab) {
		StatisticsType.Today -> LocalDate.now()
		StatisticsType.Weekly -> LocalDate.now().minusDays(7)
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
			_sessionHighLight.update { model ->
				model.copy(
					totalFocusCount = totalFocus,
					totalBreakCount = totalBreak,
					avgFocus = avgFocus,
					avgBreak = avgBreak
				)
			}
		}.launchIn(viewModelScope)
	}

	fun onTimerModeChanged(mode: TimerModes) = _graphMode.update { mode }

	private fun loadWeeklyReport(mode: TimerModes) {
		val weekStart = LocalDate.now().minusDays(7)

		repository.weeklyReport(mode = mode, start = weekStart)
			.onEach { report ->
				_weeklyGraph.update { state ->
					state.copy(isLoading = false, content = report)
				}
			}
			.launchIn(viewModelScope)
	}

	fun onDeleteOptions(events: DeleteEvents) {
		when (events) {
			is DeleteEvents.OnConfirmDelete -> deleteSessionData(events.type)

			is DeleteEvents.OnSelect -> _deleteState.update { state ->
				state.copy(showDialog = true, option = events.type)
			}

			DeleteEvents.OnUnSelect -> _deleteState.update { state ->
				state.copy(showDialog = false, option = null)
			}
		}
	}

	private fun deleteSessionData(type: StatisticsType) = viewModelScope.launch {
		val startDate = evaluateStartDate(type)

		when (val results = repository.deleteStatisticsData(startDate)) {
			is Resource.Error -> _uiEvents.emit(UiEvents.ShowSnackBar(results.errorMessage))
			is Resource.Success -> results.extras?.let { message ->
				_uiEvents.emit(UiEvents.ShowSnackBar(message))
				_deleteState.update { state -> state.copy(showDialog = false, option = null) }
			}
		}
	}

}