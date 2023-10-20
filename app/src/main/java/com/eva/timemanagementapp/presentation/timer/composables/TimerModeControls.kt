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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerModeControls(
	mode: TimerModes,
	onFocusTimer: () -> Unit,
	onBreakTimer: () -> Unit,
	onStop: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement
			.spacedBy(dimensionResource(id = R.dimen.timer_button_spacing)),
		verticalAlignment = Alignment.CenterVertically,
	) {
		when (mode) {
			TimerModes.FOCUS_MODE -> ExtendedFloatingActionButton(
				onClick = onFocusTimer,
				contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
				containerColor = MaterialTheme.colorScheme.secondaryContainer
			) {
				Text(
					text = stringResource(id = R.string.timer_start_focus),
					style = MaterialTheme.typography.titleMedium
				)
			}

			TimerModes.BREAK_MODE -> {
				ExtendedFloatingActionButton(
					onClick = onBreakTimer,
					contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
					containerColor = MaterialTheme.colorScheme.tertiaryContainer
				) {
					Text(
						text = stringResource(id = R.string.timer_start_break),
						style = MaterialTheme.typography.titleMedium
					)
				}
				ExtendedFloatingActionButton(
					onClick = onStop,
					contentColor = MaterialTheme.colorScheme.onErrorContainer,
					containerColor = MaterialTheme.colorScheme.errorContainer
				) {
					Text(
						text = stringResource(id = R.string.stop_session),
						style = MaterialTheme.typography.titleMedium
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
fun TimerModesControlsPreview(
	@PreviewParameter(TimerModesPreviewParams::class)
	mode: TimerModes
) = TimeManagementAppTheme {
	TimerModeControls(
		mode = mode,
		onFocusTimer = { },
		onBreakTimer = {},
		onStop = {}
	)
}