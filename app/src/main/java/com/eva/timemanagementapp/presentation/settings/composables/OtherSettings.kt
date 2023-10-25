package com.eva.timemanagementapp.presentation.settings.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.eva.timemanagementapp.R

fun LazyListScope.otherSettings(
	isAirplaneModeEnabled: Boolean,
) {
	item(key = R.string.session_other_settings) {
		Text(
			text = stringResource(id = R.string.session_other_settings),
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier
				.padding(vertical = dimensionResource(id = R.dimen.settings_heading_padding))
		)
	}
	item(key = R.string.airplane_mode_title) {
		SettingsSwitchOptions(
			title = stringResource(id = R.string.airplane_mode_title),
			subtitle = stringResource(id = R.string.airplane_mode_text),
			isChecked = isAirplaneModeEnabled,
			onCheckChange = {},
			isEnabled = false,
		)
	}
}