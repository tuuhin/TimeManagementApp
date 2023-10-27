package com.eva.timemanagementapp.presentation.timer.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.Alignment

val timerModeEnter = slideInVertically { height -> -height } + fadeIn(
	animationSpec = tween(
		durationMillis = 200,
		easing = LinearEasing
	)
) + expandIn(
	animationSpec = spring(dampingRatio = 1f, stiffness = Spring.StiffnessMediumLow),
	expandFrom = Alignment.Center,
	clip = false,
)


val timerModeExit = slideOutVertically { height -> height } + fadeOut(
	animationSpec = tween(
		durationMillis = 200,
		easing = LinearEasing
	)
) + shrinkOut(
	spring(dampingRatio = 1f, stiffness = Spring.StiffnessMediumLow),
	shrinkTowards = Alignment.Center,
	clip = false
)
