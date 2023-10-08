package com.eva.timemanagementapp.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.SessionDurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import com.eva.timemanagementapp.presentation.settings.composables.SessionOptionDuration
import com.eva.timemanagementapp.presentation.settings.composables.SessionOptionNumber
import com.eva.timemanagementapp.presentation.settings.composables.SettingsSwitchOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
	focusDuration: SessionDurationOption,
	breakDuration: SessionDurationOption,
	sessionCountOption: SessionNumberOption,
	isAirplaneModeEnabled: Boolean,
	onChangeSettings: (ChangeSettingsEvent) -> Unit,
	modifier: Modifier = Modifier
) {

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.navigation_route_settings)) }
			)
		},
		modifier = modifier
	) { scPadding ->
		LazyColumn(
			contentPadding = scPadding,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = dimensionResource(id = R.dimen.scaffold_padding))
		) {
			item(key = R.string.session_settings_title) {
				Text(
					text = stringResource(id = R.string.session_settings_title),
					style = MaterialTheme.typography.titleLarge,
					color = MaterialTheme.colorScheme.onSurface,
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
			item(key = R.string.session_count_title) {
				SessionOptionNumber(
					title = stringResource(id = R.string.session_count_title),
					selected = sessionCountOption,
					onSessionNumberChange = { number ->
						onChangeSettings(ChangeSettingsEvent.OnSessionCountChange(number))
					}
				)
			}
			item {
				Spacer(modifier = Modifier.height(12.dp))
			}
			item(key = R.string.session_notification_title) {
				Text(
					text = stringResource(id = R.string.session_notification_title),
					style = MaterialTheme.typography.titleLarge,
					color = MaterialTheme.colorScheme.onSurface,
				)
			}
			item(key = R.string.airplane_mode_title) {
				SettingsSwitchOptions(
					title = stringResource(id = R.string.airplane_mode_title),
					subtitle = stringResource(id = R.string.airplane_mode_text),
					isChecked = isAirplaneModeEnabled,
					onCheckChange = {},
					isEnabled = false
				)
			}
		}
	}
}


@Preview
@Composable
fun SettingsScreenPreview() {
	SettingsScreen(
		focusDuration = SessionDurationOption.TEN_MINUTES,
		breakDuration = SessionDurationOption.TEN_MINUTES,
		sessionCountOption = SessionNumberOption.TWO_TIMES,
		isAirplaneModeEnabled = false,
		onChangeSettings = {}
	)
}