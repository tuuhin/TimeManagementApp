package com.eva.timemanagementapp.presentation.navigation

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun AppBottomNavigationBar(
	onRouteSelected: (Screens) -> Unit,
	isRouteSelected: (Screens) -> Boolean,
	modifier: Modifier = Modifier,
	containerColor: Color = NavigationBarDefaults.containerColor
) {
	val items = listOf(Screens.StatisticsRoute, Screens.TimerRoute, Screens.SettingsRoute)

	NavigationBar(
		modifier = modifier,
		containerColor = containerColor,
		windowInsets = WindowInsets.navigationBars
	) {
		items.forEach { screen ->
			val isSelected = isRouteSelected(screen)
			NavigationBarItem(
				selected = isSelected,
				onClick = { onRouteSelected(screen) },
				icon = {
					Crossfade(
						targetState = isSelected,
						animationSpec = tween(easing = FastOutLinearInEasing),
						label = "Navigation Icon Transition ${screen.route}"
					) { condition ->
						Icon(
							painter = if (condition) painterResource(id = screen.activeIcon)
							else painterResource(id = screen.icon),
							contentDescription = screen.route
						)
					}
				},
				label = {
					Text(
						text = stringResource(id = screen.label),
						style = MaterialTheme.typography.titleSmall
					)
				},
				colors = NavigationBarItemDefaults.colors(
					selectedTextColor = MaterialTheme.colorScheme.secondary,
				)
			)
		}
	}
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun AppBottomNavigationBarPreview() = TimeManagementAppTheme {
	AppBottomNavigationBar(
		onRouteSelected = {},
		isRouteSelected = { it == Screens.TimerRoute },
	)
}