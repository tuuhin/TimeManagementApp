package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerModeControls(
	mode: TimerModes,
	onFocusTimer: () -> Unit,
	onBreakTimer: () -> Unit,
	onStop: () -> Unit,
	modifier: Modifier = Modifier,
	buttonSpacing: Dp = 16.dp
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(space = buttonSpacing),
		verticalAlignment = Alignment.CenterVertically
	) {
		when (mode) {
			TimerModes.FOCUS_MODE -> ExtendedFloatingActionButton(
				onClick = onFocusTimer,
				contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
				containerColor = MaterialTheme.colorScheme.secondaryContainer
			) {
				Text(text = "Start Focus", style = MaterialTheme.typography.titleMedium)
			}

			TimerModes.BREAK_MODE -> {
				ExtendedFloatingActionButton(
					onClick = onBreakTimer,
					contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
					containerColor = MaterialTheme.colorScheme.secondaryContainer
				) {
					Text(text = "Start Break", style = MaterialTheme.typography.titleMedium)
				}
				ExtendedFloatingActionButton(
					onClick = onStop,
					contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
					containerColor = MaterialTheme.colorScheme.tertiaryContainer
				) {
					Text(text = "Stop Session", style = MaterialTheme.typography.titleMedium)
				}
			}
		}
	}
}


class TimerModesPreviewParams : CollectionPreviewParameterProvider<TimerModes>(
	collection = TimerModes.entries.toList()
)

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TimerModesControlsPreview(
	@PreviewParameter(TimerModesPreviewParams::class)
	mode: TimerModes
) = TimeManagementAppTheme {
	TimerModeControls(
		mode = mode, onFocusTimer = { }, onBreakTimer = {}, onStop = {}
	)
}