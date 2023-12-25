package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import com.eva.timemanagementapp.utils.extensions.toHMFormat
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsReminderOption(
	title: String,
	time: LocalTime,
	onTimeChanged: (LocalTime) -> Unit,
	modifier: Modifier = Modifier,
	subtitle: String? = null,
	containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	shape: Shape = MaterialTheme.shapes.small,
	elevation: Dp = 2.dp,
) {

	var isTimePickerOpen by remember { mutableStateOf(false) }

	val timePickerState = rememberTimePickerState(
		initialHour = time.hour,
		initialMinute = time.minute,
	)

	val timeText by remember(time) { derivedStateOf(time::toHMFormat) }

	if (isTimePickerOpen) {
		AlertDialog(
			onDismissRequest = { isTimePickerOpen = false },
			confirmButton = {
				Button(
					onClick = {
						val pickedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
						onTimeChanged(pickedTime)
						isTimePickerOpen = false
					},
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.primaryContainer,
						contentColor = MaterialTheme.colorScheme.onPrimaryContainer
					)
				) {
					Text(
						text = stringResource(id = R.string.dialog_confirm_text),
						style = MaterialTheme.typography.titleSmall
					)
				}
			},
			dismissButton = {
				TextButton(
					onClick = { isTimePickerOpen = false },
					colors = ButtonDefaults.textButtonColors(
						contentColor = MaterialTheme.colorScheme.onSecondaryContainer
					)
				) {
					Text(
						text = stringResource(id = R.string.dialog_dismiss_text),
						style = MaterialTheme.typography.titleSmall
					)
				}
			},
			properties = DialogProperties(
				dismissOnBackPress = true,
				dismissOnClickOutside = true
			),
			text = {
				TimePicker(
					state = timePickerState,
					layoutType = TimePickerLayoutType.Vertical,
					colors = TimePickerDefaults.colors(
						containerColor = MaterialTheme.colorScheme.background,
					),
				)
			},
			title = {
				Text(
					text = stringResource(id = R.string.time_picker_heading),
					color = MaterialTheme.colorScheme.onSecondaryContainer,
					style = MaterialTheme.typography.headlineMedium
				)
			},
			shape = MaterialTheme.shapes.medium,
			containerColor = MaterialTheme.colorScheme.background
		)
	}

	SettingsOptionContainer(
		title = title,
		subtitle = subtitle,
		modifier = modifier,
		containerColor = containerColor,
		contentColor = contentColor,
		shape = shape,
		elevation = elevation,
	) {
		TextButton(
			onClick = { isTimePickerOpen = !isTimePickerOpen },
			colors = ButtonDefaults.textButtonColors(contentColor = contentColor)
		) {
			Text(
				text = timeText,
				style = MaterialTheme.typography.titleMedium,
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
fun SettingsReminderTimeOptionPreview() = TimeManagementAppTheme {
	SettingsReminderOption(
		title = "Reminder",
		time = LocalTime.of(0, 0),
		onTimeChanged = {}
	)
}