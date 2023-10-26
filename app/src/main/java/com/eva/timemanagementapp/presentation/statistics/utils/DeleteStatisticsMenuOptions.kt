package com.eva.timemanagementapp.presentation.statistics.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.eva.timemanagementapp.R

sealed class DeleteStatisticsMenuOptions(
	@StringRes val label: Int,
	val action: StatisticsType,
	@DrawableRes val icon: Int? = R.drawable.ic_delete,
) {
	data object ClearLastWeekData : DeleteStatisticsMenuOptions(
		label = R.string.clear_last_week_data,
		action = StatisticsType.Weekly
	)

	data object ClearTodayData : DeleteStatisticsMenuOptions(
		label = R.string.clear_today_data,
		action = StatisticsType.Today,
	)
}
