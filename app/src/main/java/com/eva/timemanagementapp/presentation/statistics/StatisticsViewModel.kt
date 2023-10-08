package com.eva.timemanagementapp.presentation.statistics

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor() : ViewModel() {

	private val _totalSessionCount = MutableStateFlow(0)
	val totalSessionCount = _totalSessionCount.asStateFlow()

	private val _averageSessionCount = MutableStateFlow(0)
	val averageSessionCount = _averageSessionCount.asStateFlow()
}