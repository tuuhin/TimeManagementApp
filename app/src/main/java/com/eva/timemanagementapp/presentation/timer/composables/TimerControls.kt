package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerControls(
	state: TimerWatchStates,
	onPause: () -> Unit,
	onResume: () -> Unit,
	onStart: () -> Unit,
	modifier: Modifier = Modifier,
	buttonSpacing: Dp = 16.dp
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(space = buttonSpacing)
	) {
		ExtendedFloatingActionButton(
			onClick = onStart,
			contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
			containerColor = MaterialTheme.colorScheme.secondaryContainer
		) {
			Text(text = "Start")
		}
		AnimatedVisibility(
			visible = state != TimerWatchStates.IDLE,
			enter = fadeIn() + slideInVertically(),
			exit = fadeOut() + slideOutVertically()
		) {
			FloatingActionButton(
				onClick = {
					when (state) {
						TimerWatchStates.RUNNING -> onPause()
						TimerWatchStates.PAUSED -> onResume()
						else -> {}
					}
				},
				contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
				containerColor = MaterialTheme.colorScheme.primaryContainer,
			) {
				when (state) {
					TimerWatchStates.RUNNING -> Icon(
						painter = painterResource(id = R.drawable.ic_pause),
						contentDescription = stringResource(id = R.string.icon_pause)
					)

					TimerWatchStates.PAUSED -> Icon(
						painter = painterResource(id = R.drawable.ic_play),
						contentDescription = stringResource(id = R.string.icon_play)
					)

					else -> {}
				}
			}
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TimerControlsPreview() = TimeManagementAppTheme {
	TimerControls(
		state = TimerWatchStates.RUNNING,
		onPause = { },
		onResume = { },
		onStart = { },
	)
}