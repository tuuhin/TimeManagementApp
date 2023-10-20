package com.eva.timemanagementapp.presentation.timer.composables

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeepScreenOnButton(
	modifier: Modifier = Modifier,
	onChange: ((Boolean) -> Unit)? = null,
	view: View = LocalView.current,
	containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
	contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {

	var isFlagSet by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {

		val window = (view.context as Activity).window

		isFlagSet = window.attributes.flags and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON != 0
	}

	PlainTooltipBox(
		tooltip = {
			Text(
				text = stringResource(id = R.string.turn_on_keep_screen_mode),
				style = MaterialTheme.typography.labelMedium,
				modifier = Modifier.padding(4.dp)
			)
		},
		shape = MaterialTheme.shapes.extraSmall,
		containerColor = MaterialTheme.colorScheme.inverseSurface,
		contentColor = MaterialTheme.colorScheme.inverseOnSurface
	) {
		IconButton(
			onClick = {
				val window = (view.context as Activity).window

				when {
					isFlagSet -> window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
					else -> window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
				}

				isFlagSet = window.attributes.flags and
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON != 0

				onChange?.invoke(isFlagSet)

			},
			modifier = modifier.tooltipAnchor(),
			colors = IconButtonDefaults.iconButtonColors(
				containerColor = containerColor,
				contentColor = contentColor
			),
		) {
			when {
				isFlagSet -> Icon(
					painter = painterResource(id = R.drawable.ic_sun),
					contentDescription = "Keep screen on"
				)

				else -> Icon(
					painter = painterResource(id = R.drawable.ic_sun_outlined),
					contentDescription = "Keep Screen off"
				)
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
fun KeepScreenOnButtonPreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		KeepScreenOnButton()
	}
}

