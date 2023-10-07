package com.eva.timemanagementapp.presentation.timer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.stopwatch.TimerWatchStates
import com.eva.timemanagementapp.presentation.timer.composables.TimerClockStyle
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
	state: TimerWatchStates,
	timerTime: LocalTime,
	timerDuration: LocalTime,
	modifier: Modifier = Modifier,
) {
	Scaffold(
		modifier = modifier,
		contentWindowInsets = WindowInsets.safeContent,
		topBar = { TopAppBar(title = { Text(text = "Timer") }) },
		floatingActionButtonPosition = FabPosition.Center,
		floatingActionButton = {
			LargeFloatingActionButton(
				onClick = { /*TODO*/ }
			) {
				Icon(
					painter = painterResource(id = R.drawable.ic_pause),
					contentDescription = "Pause Timer"
				)
			}
		}
	) { scPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(scPadding)
				.padding(horizontal = dimensionResource(id = R.dimen.scaffold_padding)),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			TimerClockStyle(
				currentTime = timerTime,
				timerTime = timerDuration,
				modifier = Modifier.padding(horizontal = 20.dp)
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
		timerDuration = LocalTime.of(1, 0)
	)
}