package com.eva.timemanagementapp.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.eva.timemanagementapp.R

sealed class Screens(
	val route: String,
	@StringRes val label: Int,
	@DrawableRes val activeIcon: Int,
	@DrawableRes val icon: Int
) {
	data object StatisticsRoute : Screens(
		route = "Statistics",
		label = R.string.navigation_route_statistics,
		activeIcon = R.drawable.ic_statistics_filled,
		icon = R.drawable.ic_statistics_oulined
	)

	data object TimerRoute : Screens(
		route = "Timer",
		label = R.string.navigation_route_timer,
		activeIcon = R.drawable.ic_timer_filled,
		icon = R.drawable.ic_timer_outlined
	)

	data object SettingsRoute : Screens(
		route = "Settings",
		label = R.string.navigation_route_settings,
		activeIcon = R.drawable.ic_settings_filled,
		icon = R.drawable.ic_settings_outlined
	)
}
