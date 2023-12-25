package com.eva.timemanagementapp.presentation.timer

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.presentation.timer.composables.KeepScreenOnButton
import com.eva.timemanagementapp.presentation.timer.composables.TimerClockStyle
import com.eva.timemanagementapp.presentation.timer.composables.TimerModeBanner
import com.eva.timemanagementapp.presentation.timer.composables.TimerModeControls
import com.eva.timemanagementapp.presentation.timer.composables.TimerModesPreviewParams
import com.eva.timemanagementapp.presentation.timer.composables.TimerPlayPause
import com.eva.timemanagementapp.presentation.timer.composables.timerModeEnter
import com.eva.timemanagementapp.presentation.timer.composables.timerModeExit
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

	val isTimerRunningOrPaused by remember(state) {
		derivedStateOf {
			state == TimerWatchStates.RUNNING || state == TimerWatchStates.PAUSED
		}
	}

	Scaffold(
		modifier = modifier,
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(text = stringResource(id = R.string.navigation_route_timer))
				},
				actions = { KeepScreenOnButton() }
			)
		},
	) { scPadding ->
		Box(
			modifier = Modifier
				.padding(scPadding)
				.fillMaxSize()
				.padding(all = dimensionResource(id = R.dimen.scaffold_padding)),
		) {
			AnimatedVisibility(
				visible = isTimerRunningOrPaused,
				enter = timerModeEnter,
				exit = timerModeExit,
				modifier = Modifier.align(Alignment.TopCenter)
			) {
				TimerModeBanner(
					mode = mode,
					elevation = 2.dp,
					shape = MaterialTheme.shapes.large,
				)
			}
			TimerClockStyle(
				currentTime = timerTime,
				timerTime = timerDuration,
				state = state,
				modifier = Modifier
					.align(Alignment.Center)
					.fillMaxWidth(0.9f)
					.padding(all = dimensionResource(id = R.dimen.timer_watch_padding)),
			)
			AnimatedContent(
				targetState = state,
				label = "Translate the type of actions",
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.offset(y = 20.dp),
				transitionSpec = {
					val noTransition = ContentTransform(
						targetContentEnter = EnterTransition.None,
						initialContentExit = ExitTransition.None
					)

					val fromPlayToPause =
						initialState == TimerWatchStates.RUNNING && targetState == TimerWatchStates.PAUSED
					val fromPauseToPlay =
						initialState == TimerWatchStates.PAUSED && targetState == TimerWatchStates.RUNNING
					if (fromPauseToPlay || fromPlayToPause)
						return@AnimatedContent noTransition

					if (targetState > initialState) {
						slideInVertically { height -> height } + fadeIn() togetherWith
								slideOutVertically { height -> -height } +
								fadeOut()
					} else {
						slideInVertically { height -> -height } + fadeIn() togetherWith
								slideOutVertically { height -> height } +
								fadeOut()
					} using SizeTransform(clip = false)
				},
			) { watchStates ->
				when (watchStates) {
					TimerWatchStates.IDLE, TimerWatchStates.COMPLETED -> TimerModeControls(
						mode = mode,
						onFocusTimer = { onTimerEvents(TimerEvents.OnFocusModeStart) },
						onBreakTimer = { onTimerEvents(TimerEvents.OnBreakModeStart) },
						onStop = { onTimerEvents(TimerEvents.OnStopSession) },
						modifier = Modifier.padding(20.dp)
					)

					else -> TimerPlayPause(
						state = watchStates,
						onPause = { onTimerEvents(TimerEvents.OnPause) },
						onResume = { onTimerEvents(TimerEvents.OnResume) },
						onStop = { onTimerEvents(TimerEvents.OnStopSession) },
						modifier = Modifier.padding(20.dp)
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
fun TimerScreenPreview(
	@PreviewParameter(TimerModesPreviewParams::class) mode: TimerModes,
) = TimeManagementAppTheme {
	TimerScreen(
		mode = mode,
		state = TimerWatchStates.RUNNING,
		timerTime = LocalTime.of(0, 30),
		timerDuration = LocalTime.of(1, 0),
		onTimerEvents = {},
	)
}
