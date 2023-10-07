package com.eva.timemanagementapp.presentation.navigation

import android.content.res.Configuration
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
fun TimerBottomNavigation(
	onRouteSelected: (Screens) -> Unit,
	isRouteSelected: (Screens) -> Boolean,
	modifier: Modifier = Modifier,
	containerColor: Color = NavigationBarDefaults.containerColor
) {
	val items = listOf(Screens.StatisticsRoute, Screens.TimerRoute, Screens.SettingsRoute)

	NavigationBar(
		modifier = modifier,
		containerColor = containerColor
	) {
		items.forEach { screen ->
			val isSelected = isRouteSelected(screen)
			NavigationBarItem(
				selected = isSelected,
				onClick = { onRouteSelected(screen) },
				icon = {
					Icon(
						painter = if (isSelected) painterResource(id = screen.activeIcon)
						else painterResource(id = screen.icon),
						contentDescription = screen.route
					)
				},
				label = { Text(text = stringResource(id = screen.label)) },
				alwaysShowLabel = false,
				colors = NavigationBarItemDefaults.colors(
					selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
					selectedTextColor = MaterialTheme.colorScheme.primary,
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
fun TimerBottomNavigationPreview() = TimeManagementAppTheme {
	TimerBottomNavigation(
		onRouteSelected = {},
		isRouteSelected = { it == Screens.TimerRoute },
	)
}