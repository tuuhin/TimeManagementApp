package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import java.time.LocalTime

@Composable
fun TimerClockStyle(
	currentTime: LocalTime,
	timerTime: LocalTime,
	modifier: Modifier = Modifier,
) {
	val angle by remember(currentTime, timerTime) {
		derivedStateOf {
			val currentTimeSeconds = currentTime.toSecondOfDay().toFloat()
			val timerTimeSeconds = timerTime.toSecondOfDay().toFloat()
			val angle = (timerTimeSeconds - currentTimeSeconds) / timerTimeSeconds * 360f
			angle
		}
	}

	Box(
		modifier = modifier.aspectRatio(1f),
		contentAlignment = Alignment.Center
	) {
		TimerClockDial(coveredAngle = angle, modifier = Modifier.fillMaxSize())
		TimerClockFace(time = currentTime)
	}

}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TimerClockStylePreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		TimerClockStyle(
			currentTime = LocalTime.of(0, 5),
			timerTime = LocalTime.of(0, 10),
			modifier = Modifier
				.padding(40.dp)
				.fillMaxWidth()
				.aspectRatio(1f)
		)
	}
}