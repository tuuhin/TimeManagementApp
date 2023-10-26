package com.eva.timemanagementapp.presentation.statistics.utils

sealed interface DeleteEvents {

	data class OnSelect(val type: StatisticsType) : DeleteEvents

	data object OnUnSelect : DeleteEvents

	data class OnConfirmDelete(val type: StatisticsType) : DeleteEvents
}