package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.presentation.statistics.utils.StatisticsType
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun DeleteStatisticsDialog(
	option: StatisticsType,
	onDelete: (StatisticsType) -> Unit,
	onDismissRequest: () -> Unit,
	modifier: Modifier = Modifier,
	containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
	contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
	confirmButtonColor: Color = MaterialTheme.colorScheme.tertiary,
	onConfirmButtonColor: Color = MaterialTheme.colorScheme.onTertiary,
) {
	AlertDialog(
		onDismissRequest = onDismissRequest,
		confirmButton = {
			Button(
				onClick = { onDelete(option) },
				colors = ButtonDefaults.buttonColors(
					containerColor = confirmButtonColor,
					contentColor = onConfirmButtonColor
				)
			) {
				Text(text = stringResource(id = R.string.delete_statistics_confirm_button_text))
			}
		},
		dismissButton = {
			TextButton(
				onClick = onDismissRequest,
				colors = ButtonDefaults.textButtonColors(contentColor = contentColor)
			) {
				Text(text = stringResource(id = R.string.delete_statistics_dismiss_button_text))
			}
		},
		title = {
			Text(
				text = stringResource(id = R.string.delete_statistics_title),
				style = MaterialTheme.typography.headlineSmall
			)
		},
		text = {
			when (option) {
				StatisticsType.Weekly -> Text(
					text = stringResource(id = R.string.delete_statistics_text_week),
					style = MaterialTheme.typography.bodyMedium
				)

				StatisticsType.Today -> Text(
					text = stringResource(id = R.string.delete_statistics_text_today),
					style = MaterialTheme.typography.bodyMedium
				)

				else -> {}
			}
		},
		modifier = modifier,
		shape = MaterialTheme.shapes.medium,
		containerColor = containerColor,
		titleContentColor = contentColor,
		textContentColor = contentColor,
		properties = DialogProperties(dismissOnBackPress = false),
	)
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun DeleteStatisticsDialogPreview() = TimeManagementAppTheme {
	DeleteStatisticsDialog(
		option = StatisticsType.Weekly,
		onDismissRequest = {},
		onDelete = {}
	)
}