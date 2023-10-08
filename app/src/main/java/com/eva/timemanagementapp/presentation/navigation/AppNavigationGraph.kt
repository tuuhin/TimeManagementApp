package com.eva.timemanagementapp.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eva.timemanagementapp.data.services.SessionService
import com.eva.timemanagementapp.presentation.settings.SettingsScreen
import com.eva.timemanagementapp.presentation.settings.SettingsViewModel
import com.eva.timemanagementapp.presentation.statistics.StatisticsScreen
import com.eva.timemanagementapp.presentation.statistics.StatisticsViewModel
import com.eva.timemanagementapp.presentation.timer.TimerScreen
import com.eva.timemanagementapp.presentation.timer.TimerViewModel

@Composable
fun AppNavigationGraph(
	service: SessionService,
	modifier: Modifier = Modifier
) {
	val navController = rememberNavController()
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	Scaffold(
		bottomBar = {
			AppBottomNavigationBar(
				onRouteSelected = { screen ->
					navController.navigate(screen.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = true
						}
						launchSingleTop = true
						restoreState = true
					}
				},
				isRouteSelected = { screen ->
					navBackStackEntry?.destination?.hierarchy?.any { destination ->
						destination.route == screen.route
					} == true
				}
			)
		}
	) { scPadding ->
		NavHost(
			navController = navController,
			startDestination = Screens.TimerRoute.route,
			modifier = modifier.padding(scPadding)
		) {
			composable(
				route = Screens.StatisticsRoute.route,
				enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
				exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
			) {

				val viewModel = hiltViewModel<StatisticsViewModel>()

				val totalSessions by viewModel.totalSessionCount.collectAsStateWithLifecycle()
				val dailyAverage by viewModel.averageSessionCount.collectAsStateWithLifecycle()

				StatisticsScreen(totalSessions = totalSessions, averageSessions = dailyAverage)
			}
			composable(
				route = Screens.TimerRoute.route,
				enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
				exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
			) {

				val viewModel = hiltViewModel<TimerViewModel>()

				val timerDuration by viewModel.timerDuration.collectAsStateWithLifecycle()

				val state by service.stopWatch.state.collectAsStateWithLifecycle()
				val elapsedTime by service.stopWatch.elapsedTime.collectAsStateWithLifecycle()

				TimerScreen(
					state = state,
					timerTime = elapsedTime,
					timerDuration = timerDuration,
					onTimerEvents = viewModel::onTimerEvents
				)
			}
			composable(
				route = Screens.SettingsRoute.route,
				enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
				exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
			) {
				val settingsViewModel = hiltViewModel<SettingsViewModel>()

				val focusDuration by settingsViewModel.focusDuration.collectAsStateWithLifecycle()
				val breakDuration by settingsViewModel.breakDuration.collectAsStateWithLifecycle()
				val sessionCount by settingsViewModel.sessionCount.collectAsStateWithLifecycle()
				val airplaneMode by settingsViewModel.isAirPlaneModeEnabled.collectAsStateWithLifecycle()

				SettingsScreen(
					focusDuration = focusDuration,
					breakDuration = breakDuration,
					sessionCountOption = sessionCount,
					isAirplaneModeEnabled = airplaneMode,
					onChangeSettings = settingsViewModel::onChangeSettingsEvent
				)
			}
		}
	}
}