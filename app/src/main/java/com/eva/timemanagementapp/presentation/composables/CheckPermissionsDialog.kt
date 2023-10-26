package com.eva.timemanagementapp.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun CheckPermissionsDialog(
	onDismiss: () -> Unit,
	onShowSettings: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			Button(
				onClick = onShowSettings,
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primaryContainer,
					contentColor = MaterialTheme.colorScheme.onPrimaryContainer
				)
			) {
				Text(text = stringResource(id = R.string.request_permission_button_text))
			}
		},
		title = {
			Text(
				text = stringResource(id = R.string.no_notification_perms_title),
				style = MaterialTheme.typography.titleLarge
			)
		},
		text = {
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Image(
					painter = painterResource(id = R.drawable.ic_notification_bell),
					contentDescription = null,
					colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
				)
				Text(
					text = stringResource(id = R.string.no_notification_perms_text),
					style = MaterialTheme.typography.bodyLarge
				)
			}
		}
	)
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun CheckPermissionsDialogPreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		CheckPermissionsDialog(
			onDismiss = {},
			onShowSettings = {}
		)
	}
}