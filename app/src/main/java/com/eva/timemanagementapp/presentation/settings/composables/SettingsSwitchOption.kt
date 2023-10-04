package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
	contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
	containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
	elevation: Dp = 0.dp,
	shape: Shape = MaterialTheme.shapes.small
) {
	Card(
		modifier = modifier,
		elevation = CardDefaults.cardElevation(defaultElevation = elevation),
		colors = CardDefaults.cardColors(
			containerColor = containerColor,
			contentColor = contentColor
		),
		shape = shape,
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(all = dimensionResource(id = R.dimen.settings_card_padding)),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Column(
				verticalArrangement = Arrangement.spacedBy(2.dp),
				modifier = Modifier.weight(.7f)
			) {
				Text(
					text = title,
					style = MaterialTheme.typography.titleMedium
				)
				subtitle?.let {
					Text(
						text = it,
						style = MaterialTheme.typography.bodyMedium,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}
			Switch(
				checked = isChecked,
				onCheckedChange = onCheckChange,
				enabled = isEnabled,
				modifier = Modifier.weight(.2f),
				colors = SwitchDefaults.colors(
					disabledCheckedTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
					disabledCheckedThumbColor = MaterialTheme.colorScheme.primaryContainer,
				)
			)
		}
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
