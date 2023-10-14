package com.eva.timemanagementapp.presentation.timer

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.presentation.timer.composables.TimerClockStyle
import com.eva.timemanagementapp.presentation.timer.composables.TimerModeBanner
import com.eva.timemanagementapp.presentation.timer.composables.TimerModeControls
import com.eva.timemanagementapp.presentation.timer.composables.TimerModesPreviewParams
import com.eva.timemanagementapp.presentation.timer.composables.TimerPlayPause
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
	mode: TimerModes,
	state: TimerWatchStates,
	timerTime: LocalTime,
	timerDuration: LocalTime,
	modifier: Modifier = Modifier,
	onTimerEvents: (TimerEvents) -> Unit,
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.navigation_route_timer)) },
				actions = {
					PlainTooltipBox(
						tooltip = { Text(text = "Keep Screen On") },
						containerColor = MaterialTheme.colorScheme.inverseSurface,
						contentColor = MaterialTheme.colorScheme.inverseOnSurface
					) {
						IconButton(
							onClick = {

							},
							colors = IconButtonDefaults.iconButtonColors(
								contentColor = MaterialTheme.colorScheme.onSurfaceVariant
							)
						) {
							Icon(
								painter = painterResource(id = R.drawable.ic_sun),
								contentDescription = "keep screen On"
							)
						}
					}
				}
			)
		},
	) { scPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(scPadding)
				.padding(horizontal = dimensionResource(id = R.dimen.scaffold_padding)),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {

			TimerModeBanner(modes = mode)
			Spacer(modifier = Modifier.height(20.dp))
			TimerClockStyle(
				currentTime = timerTime,
				timerTime = timerDuration,
				state = state,
				modifier = Modifier.padding(all = dimensionResource(id = R.dimen.timer_watch_padding)),
			)
			Spacer(modifier = Modifier.height(20.dp))
			AnimatedVisibility(
				visible = state in listOf(TimerWatchStates.IDLE, TimerWatchStates.COMPLETED),
				enter = slideInVertically(),
				exit = slideOutVertically()
			) {
				TimerModeControls(
					mode = mode,
					onFocusTimer = { onTimerEvents(TimerEvents.OnFocusModeStart) },
					onBreakTimer = { onTimerEvents(TimerEvents.OnBreakModeStart) },
					onStop = { onTimerEvents(TimerEvents.OnStopSession) },
				)
			}
			Spacer(modifier = Modifier.height(20.dp))
			TimerPlayPause(
				state = state,
				onPause = { onTimerEvents(TimerEvents.OnPause) },
				onResume = { onTimerEvents(TimerEvents.OnResume) },
			)
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TimerScreenPreview(
	@PreviewParameter(TimerModesPreviewParams::class)
	mode: TimerModes,
) = TimeManagementAppTheme {
	TimerScreen(
		mode = mode,
		state = TimerWatchStates.RUNNING,
		timerTime = LocalTime.of(0, 30),
		timerDuration = LocalTime.of(1, 0),
		onTimerEvents = {},
	)
}