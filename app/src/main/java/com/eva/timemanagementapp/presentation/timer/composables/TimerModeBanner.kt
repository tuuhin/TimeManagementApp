package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerModeBanner(
	modes: TimerModes,
	modifier: Modifier = Modifier,
	containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
	contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
	textStyle: TextStyle = MaterialTheme.typography.titleMedium,
	elevation: Dp = 4.dp,
	shape: Shape = MaterialTheme.shapes.large
) {
	Card(
		modifier,
		colors = CardDefaults.cardColors(
			containerColor = containerColor,
			contentColor = contentColor
		),
		shape = shape,
		elevation = CardDefaults.cardElevation(defaultElevation = elevation),
	) {
		Crossfade(
			targetState = modes,
			label = "Mode Fade Transition",
			animationSpec = TweenSpec(durationMillis = 200, easing = EaseInOutCubic)
		) { mode ->
			Row(
				horizontalArrangement = Arrangement.spacedBy(12.dp),
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.padding(all = dimensionResource(id = R.dimen.mode_padding)),
			) {
				when (mode) {
					TimerModes.FOCUS_MODE -> {
						Image(
							painter = painterResource(id = R.drawable.ic_focus),
							contentDescription = stringResource(id = R.string.timer_focus_mode),
							colorFilter = ColorFilter.tint(color = contentColor)
						)
						Text(
							text = stringResource(id = R.string.timer_focus_mode),
							style = textStyle
						)
					}

					TimerModes.BREAK_MODE -> {
						Image(
							painter = painterResource(id = R.drawable.ic_break),
							contentDescription = stringResource(id = R.string.timer_break_mode),
							colorFilter = ColorFilter.tint(color = contentColor)
						)
						Text(
							text = stringResource(id = R.string.timer_break_mode),
							style = textStyle
						)
					}
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
fun TimerModeBannerPreview(
	@PreviewParameter(TimerModesPreviewParams::class)
	mode: TimerModes
) = TimeManagementAppTheme {
	TimerModeBanner(modes = mode)
}