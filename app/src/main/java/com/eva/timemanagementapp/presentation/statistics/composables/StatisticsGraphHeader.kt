package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun StatisticsGraphHeading(
	selectedMode: TimerModes,
	onModeChange: (TimerModes) -> Unit,
	modifier: Modifier = Modifier,
) {
	var showDropDown by remember { mutableStateOf(false) }

	Row(
		modifier = modifier
			.padding(horizontal = dimensionResource(id = R.dimen.statistics_header_padding)),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(2.dp),
			modifier = Modifier.weight(.7f)
		) {
			Text(
				text = stringResource(id = R.string.highlights_graph),
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurface
			)
			Text(
				text = stringResource(id = R.string.statistics_graph_subtext),
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
		Box(
			modifier = Modifier.wrapContentSize(),
			contentAlignment = Alignment.Center
		) {
			Button(
				onClick = { showDropDown = true },
				colors = ButtonDefaults
					.buttonColors(
						contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
						containerColor = MaterialTheme.colorScheme.tertiaryContainer
					),
				shape = MaterialTheme.shapes.medium,
				elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
				contentPadding = PaddingValues(6.dp)
			) {
				when (selectedMode) {
					TimerModes.FOCUS_MODE -> Text(
						text = stringResource(id = R.string.timer_focus_mode),
						style = MaterialTheme.typography.bodyMedium
					)

					TimerModes.BREAK_MODE -> Text(
						text = stringResource(id = R.string.timer_break_mode),
						style = MaterialTheme.typography.bodyMedium
					)
				}
			}
			DropdownMenu(
				expanded = showDropDown,
				onDismissRequest = { showDropDown = false },
				properties = PopupProperties(
					dismissOnClickOutside = true,
					dismissOnBackPress = true,
				)
			) {
				TimerModes.entries.forEach { item ->
					DropdownMenuItem(
						text = {
							when (item) {
								TimerModes.FOCUS_MODE -> Text(
									text = stringResource(id = R.string.timer_focus_mode),
									style = MaterialTheme.typography.titleSmall
								)

								TimerModes.BREAK_MODE -> Text(
									text = stringResource(id = R.string.timer_break_mode),
									style = MaterialTheme.typography.titleSmall
								)
							}

						},
						onClick = { onModeChange(item) }
					)
				}
			}
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
fun StatisticsGraphHeadingPreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.surface) {
		StatisticsGraphHeading(
			selectedMode = TimerModes.FOCUS_MODE,
			onModeChange = {},
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 4.dp)
		)
	}
}

