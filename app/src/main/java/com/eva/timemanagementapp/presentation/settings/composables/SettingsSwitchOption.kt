package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun SettingsSwitchOptions(
	title: String, isChecked: Boolean,
	onCheckChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	subtitle: String? = null,
	isEnabled: Boolean = true,
	containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	shape: Shape = MaterialTheme.shapes.small,
	elevation: Dp = 0.dp,
) {
	SettingsOptionContainer(
		title = title,
		subtitle = subtitle,
		modifier = modifier,
		containerColor = containerColor,
		contentColor = contentColor,
		shape = shape,
		elevation = elevation,
	) {
		Switch(
			checked = isChecked,
			onCheckedChange = onCheckChange,
			enabled = isEnabled,
		)
	}
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SettingsSwitchOptionPreview() = TimeManagementAppTheme {
	SettingsSwitchOptions(
		title = stringResource(id = R.string.airplane_mode_title),
		subtitle = stringResource(id = R.string.airplane_mode_text),
		isChecked = true,
		isEnabled = false,
		onCheckChange = {},
		modifier = Modifier.fillMaxWidth()
	)
}
