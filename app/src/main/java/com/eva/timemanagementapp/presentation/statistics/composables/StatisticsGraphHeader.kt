package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
		modifier = modifier,
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(2.dp)
		) {
			Text(
				text = stringResource(id = R.string.highlights_graph),
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurface
			)
			Text(
				text = "Current week stats",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
		Box(
			modifier = Modifier.wrapContentSize(),
			contentAlignment = Alignment.Center
		) {
			TextButton(
				onClick = { showDropDown = true },
				colors = ButtonDefaults
					.textButtonColors(
						contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
						containerColor = MaterialTheme.colorScheme.secondaryContainer
					),
				modifier = Modifier.padding(vertical = 4.dp)
			) {
				Text(
					text = when (selectedMode) {
						TimerModes.FOCUS_MODE -> stringResource(id = R.string.timer_focus_mode)
						TimerModes.BREAK_MODE -> stringResource(id = R.string.timer_break_mode)
					},
					style = MaterialTheme.typography.titleSmall,
				)
			}
			DropdownMenu(
				expanded = showDropDown,
				onDismissRequest = { showDropDown = !showDropDown },
			) {
				TimerModes.entries.forEach { item ->
					DropdownMenuItem(
						text = {
							Text(
								text = when (item) {
									TimerModes.FOCUS_MODE -> stringResource(id = R.string.timer_focus_mode)
									TimerModes.BREAK_MODE -> stringResource(id = R.string.timer_break_mode)
								}
							)
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

