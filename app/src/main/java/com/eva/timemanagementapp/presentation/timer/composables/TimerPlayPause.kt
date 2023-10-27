package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerPlayPause(
	state: TimerWatchStates,
	onPause: () -> Unit,
	onResume: () -> Unit,
	onStop: () -> Unit,
	modifier: Modifier = Modifier,
	primaryIconColor: Color = MaterialTheme.colorScheme.primaryContainer,
	onPrimaryIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	secondaryIconColor: Color = MaterialTheme.colorScheme.secondaryContainer,
	onSecondaryIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		when (state) {
			TimerWatchStates.RUNNING -> FloatingActionButton(
				onClick = onPause,
				contentColor = onPrimaryIconColor,
				containerColor = primaryIconColor,
				elevation = FloatingActionButtonDefaults.loweredElevation()
			) {
				Icon(
					painter = painterResource(id = R.drawable.ic_pause),
					contentDescription = stringResource(id = R.string.icon_pause)
				)
			}

			TimerWatchStates.PAUSED -> FloatingActionButton(
				onClick = onResume,
				contentColor = onPrimaryIconColor,
				containerColor = primaryIconColor,
				elevation = FloatingActionButtonDefaults.loweredElevation()
			) {
				Icon(
					painter = painterResource(id = R.drawable.ic_play),
					contentDescription = stringResource(id = R.string.icon_play)
				)
			}

			else -> {}
		}
		FloatingActionButton(
			onClick = onStop,
			contentColor = onSecondaryIconColor,
			containerColor = secondaryIconColor,
			elevation = FloatingActionButtonDefaults.loweredElevation()
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_stop),
				contentDescription = "stop icon"
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
fun TimerPlayPausePreview(
	@PreviewParameter(TimerPlayPausePreviewParams::class)
	state: TimerWatchStates
) = TimeManagementAppTheme {
	TimerPlayPause(state = state, onPause = {}, onResume = { }, onStop = {})
}