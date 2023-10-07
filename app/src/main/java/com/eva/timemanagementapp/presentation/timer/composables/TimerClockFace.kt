package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import java.time.LocalTime

@Composable
fun TimerClockFace(
	time: LocalTime,
	modifier: Modifier = Modifier,
	hourTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
	hourColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	minuteTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
	minutesColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
	secondsTextStyle: TextStyle = MaterialTheme.typography.displayLarge,
	secondsColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
	separatorColor: Color = MaterialTheme.colorScheme.tertiary,
	separatorStyle: TextStyle = MaterialTheme.typography.displaySmall
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		AnimatedContent(
			targetState = time.hour,
			label = "Hour Text Animation",
			transitionSpec = { incrementAnimation() },
		) { hour ->
			Text(
				text = "$hour".padStart(2, '0'),
				style = hourTextStyle,
				color = hourColor,
			)
		}
		Text(
			text = ":",
			style = separatorStyle,
			color = separatorColor,
			modifier = Modifier.padding(horizontal = 2.dp)
		)
		AnimatedContent(
			targetState = time.minute,
			label = "Minute Text Animation",
			transitionSpec = { incrementAnimation() },
		) { minutes ->
			Text(
				text = "$minutes".padStart(2, '0'),
				style = minuteTextStyle,
				color = minutesColor,
			)
		}
		Text(
			text = ":",
			style = separatorStyle,
			color = separatorColor,
			modifier = Modifier.padding(horizontal = 2.dp)
		)
		AnimatedContent(
			targetState = time.second,
			label = "Seconds Text Animation",
			transitionSpec = { incrementAnimation() },
		) { seconds ->
			Text(
				text = "$seconds".padStart(2, '0'),
				style = secondsTextStyle,
				color = secondsColor,
			)
		}
	}
}

private fun AnimatedContentTransitionScope<Int>.incrementAnimation(): ContentTransform {
	return if (targetState > initialState) {
		slideInVertically { height -> height } + fadeIn() togetherWith slideOutVertically { height -> -height } + fadeOut()
	} else {
		slideInVertically { height -> -height } + fadeIn() togetherWith slideOutVertically { height -> height } + fadeOut()
	} using SizeTransform(clip = false)
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun TimerClockFacePreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		TimerClockFace(
			time = LocalTime.of(1, 10),
			modifier = Modifier.padding(10.dp),
		)
	}
}