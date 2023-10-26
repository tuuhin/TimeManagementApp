package com.eva.timemanagementapp.presentation.statistics.utils

data class DeleteStatisticsState(
	val showDialog: Boolean = false,
	val option: StatisticsType? = null,
)