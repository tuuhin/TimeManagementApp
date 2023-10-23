package com.eva.timemanagementapp.presentation.statistics

import androidx.annotation.StringRes
import com.eva.timemanagementapp.R

sealed class StatisticsTabs(@StringRes val label: Int, val tabIndex: Int) {
	data object All : StatisticsTabs(label = R.string.statistics_all, tabIndex = 0)
	data object Weekly : StatisticsTabs(label = R.string.statistics_weekly, tabIndex = 1)
	data object Today : StatisticsTabs(label = R.string.statistics_today, tabIndex = 2)
}
