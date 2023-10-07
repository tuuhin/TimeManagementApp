package com.eva.timemanagementapp.presentation.timer.composables

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun TimerClockDial(
	coveredAngle: Float,
	modifier: Modifier = Modifier,
	dialWidth: Float = 16f,
	shadowColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	primaryDialColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
	coverDialColor: Color = MaterialTheme.colorScheme.primaryContainer
) {

	val animatedAngle by animateFloatAsState(
		targetValue = coveredAngle,
		label = "Clock Animation",
		animationSpec = tween(
			easing = FastOutLinearInEasing,
			durationMillis = 100
		)
	)

	Spacer(modifier = modifier
		.aspectRatio(1f)
		.drawWithCache {

			val paint = Paint().apply {
				style = PaintingStyle.Stroke
				strokeWidth = dialWidth
				color = coverDialColor
				strokeCap = StrokeCap.Round
			}

			paint.asFrameworkPaint()
				.apply {
					val shadowRadius = dialWidth + 5
					setShadowLayer(
						shadowRadius,
						0f,
						0f,
						shadowColor
							.copy(alpha = 0.5f)
							.toArgb()
					)
				}

			onDrawBehind {
				drawCircle(
					color = primaryDialColor,
					style = Stroke(cap = StrokeCap.Round, width = 8f)
				)
				drawIntoCanvas { canvas ->
					val rect = Rect(Offset.Zero, size)
					canvas.drawArc(
						rect = rect,
						startAngle = 270f,
						sweepAngle = animatedAngle,
						useCenter = false,
						paint = paint
					)
				}
			}
		}
	)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TimerClockDialPreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		TimerClockDial(
			coveredAngle = 45f,
			modifier = Modifier
				.padding(10.dp)
				.fillMaxWidth()

		)
	}
}