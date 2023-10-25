package com.eva.timemanagementapp.presentation.settings.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.presentation.settings.ChangeSettingsEvent

fun LazyListScope.setSessionSettings(
	focusDuration: DurationOption,
	breakDuration: DurationOption,
	isSaveAllowed: Boolean,
	onChangeSettings: (ChangeSettingsEvent) -> Unit,
) {
	item(key = R.string.session_settings_title) {
		Text(
			text = stringResource(id = R.string.session_settings_title),
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier
				.padding(vertical = dimensionResource(id = R.dimen.settings_heading_padding))
		)
	}
	item(key = R.string.session_focus_title) {
		SessionOptionDuration(
			title = stringResource(id = R.string.session_focus_title),
			selected = focusDuration,
			onSessionDurationChange = { duration ->
				onChangeSettings(ChangeSettingsEvent.OnFocusDurationChange(duration))
			}
		)
	}
	item(key = R.string.session_break_title) {
		SessionOptionDuration(
			title = stringResource(id = R.string.session_break_title),
			selected = breakDuration,
			onSessionDurationChange = { duration ->
				onChangeSettings(ChangeSettingsEvent.OnBreakDurationChange(duration))
			}
		)
	}

	item(key = R.string.allow_save_session_data) {
		SettingsSwitchOptions(
			title = stringResource(id = R.string.allow_save_session_data),
			subtitle = stringResource(id = R.string.allow_save_session_desc),
			isChecked = isSaveAllowed,
			onCheckChange = { isAllowed ->
				onChangeSettings(ChangeSettingsEvent.IsSaveSessionAllowed(isAllowed))
			}
		)
	}
}