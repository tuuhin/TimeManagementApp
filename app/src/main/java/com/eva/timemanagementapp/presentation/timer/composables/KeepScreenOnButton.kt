package com.eva.timemanagementapp.presentation.timer.composables

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import android.view.WindowManager
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun KeepScreenOnButton(
	modifier: Modifier = Modifier,
	view: View = LocalView.current,
	containerColor: Color = Color.Transparent,
	contentColor: Color = MaterialTheme.colorScheme.primary
) {

	var isFlagSet by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) {

		val window = (view.context as Activity).window

		isFlagSet = window.attributes.flags and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON != 0
	}

	IconButton(
		onClick = {
			val window = (view.context as Activity).window

			when {
				isFlagSet -> window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
				else -> window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
			}

			isFlagSet = window.attributes.flags and
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON != 0

		},
		modifier = modifier,
		colors = IconButtonDefaults.iconButtonColors(
			containerColor = containerColor,
			contentColor = contentColor
		)
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

