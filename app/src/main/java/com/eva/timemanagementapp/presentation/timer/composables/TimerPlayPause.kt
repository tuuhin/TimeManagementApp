package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerPlayPause(
	state: TimerWatchStates,
	onPause: () -> Unit,
	onResume: () -> Unit,
	modifier: Modifier = Modifier
) {
	AnimatedVisibility(
		visible = state !in listOf(TimerWatchStates.IDLE, TimerWatchStates.COMPLETED),
		enter = fadeIn() + slideInHorizontally(),
		exit = fadeOut() + slideOutHorizontally()
	) {
		Row(
			modifier = modifier,
			horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
			verticalAlignment = Alignment.CenterVertically
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


class TimerPlayPauseStates :
	CollectionPreviewParameterProvider<TimerWatchStates>(
		listOf(TimerWatchStates.PAUSED, TimerWatchStates.RUNNING)
	)

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TimerPlayPausePreview(
	@PreviewParameter(TimerPlayPauseStates::class)
	state: TimerWatchStates
) = TimeManagementAppTheme {
	TimerPlayPause(state = state, onPause = {}, onResume = { })
}