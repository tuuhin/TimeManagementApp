package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun SessionOptionDuration(
	title: String,
	selected: DurationOption,
	onSessionDurationChange: (DurationOption) -> Unit,
	modifier: Modifier = Modifier,
	containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	shape: Shape = MaterialTheme.shapes.small,
	elevation: Dp = 0.dp,
) {
	var menuAnchor by remember { mutableStateOf(DpOffset.Zero) }

	var isDropDownVisible by remember { mutableStateOf(false) }

	SettingsOptionContainer(
		title = title,
		subtitle = "${selected.minutes} ${DurationOption.TIME_UNIT}",
		modifier = modifier,
		containerColor = containerColor,
		contentColor = contentColor,
		shape = shape,
		elevation = elevation,
	) {
		IconButton(
			onClick = { isDropDownVisible = true },
			modifier = Modifier.pointerInput(Unit) {
				detectTapGestures { offset ->
					menuAnchor = DpOffset(offset.x.dp, offset.y.dp)
				}
			},
		) {
			Icon(
				imageVector = Icons.Default.MoreVert,
				contentDescription = stringResource(id = R.string.settings_option_cnt_desc),
			)
		}
		DropdownMenu(
			expanded = isDropDownVisible,
			onDismissRequest = { isDropDownVisible = false },
			properties = PopupProperties(
				dismissOnBackPress = false,
				dismissOnClickOutside = true
			),
			offset = menuAnchor,
		) {
			DurationOption.entries.forEach { duration ->
				DropdownMenuItem(
					text = { Text(text = "${duration.minutes} ${DurationOption.TIME_UNIT}") },
					onClick = { onSessionDurationChange(duration) },
					contentPadding = PaddingValues(
						all = dimensionResource(id = R.dimen.menu_item_padding)
					),
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
fun SessionOptionDurationPreview() = TimeManagementAppTheme {
	SessionOptionDuration(
		title = "Focus Duration",
		selected = DurationOption.FIFTEEN_MINUTES,
		onSessionDurationChange = {},
		modifier = Modifier.fillMaxWidth(),
	)
}
