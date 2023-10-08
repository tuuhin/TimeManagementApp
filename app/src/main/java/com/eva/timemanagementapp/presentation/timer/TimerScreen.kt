package com.eva.timemanagementapp.presentation.timer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.presentation.timer.composables.TimerClockStyle
import com.eva.timemanagementapp.presentation.timer.composables.TimerControls
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
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
			TimerClockStyle(
				currentTime = timerTime,
				timerTime = timerDuration,
				modifier = Modifier.padding(all = dimensionResource(id = R.dimen.timer_watch_padding)),
			)
			Spacer(modifier = Modifier.height(20.dp))
			TimerControls(
				state = state,
				onPause = { onTimerEvents(TimerEvents.OnPause) },
				onResume = { onTimerEvents(TimerEvents.OnResume) },
				onStart = { onTimerEvents(TimerEvents.OnStart) },
			)
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TimerScreenPreview() = TimeManagementAppTheme {
	TimerScreen(
		state = TimerWatchStates.RUNNING,
		timerTime = LocalTime.of(0, 30),
		timerDuration = LocalTime.of(1, 0),
		onTimerEvents = {},
	)
}