package com.eva.timemanagementapp.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
	val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = stringResource(id = R.string.settings_title)) },
				scrollBehavior = scrollBehavior
			)
		},
		modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		contentWindowInsets = WindowInsets.safeContent
	) { scPadding ->
		LazyColumn(
			contentPadding = scPadding,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.fillMaxSize()
				.padding(10.dp)
		) {
			item {
				Text(
					text = stringResource(id = R.string.session_settings_title),
					style = MaterialTheme.typography.titleLarge,
					color = MaterialTheme.colorScheme.onSurface,
				)
			}
			item {
				SessionOptionDuration(
					title = stringResource(id = R.string.session_focus_duration),
					selected = focusDuration,
					onSessionDurationChange = { duration ->
						onChangeSettings(ChangeSettingsEvent.OnFocusDurationChange(duration))
					}
				)
			}
			item {
				SessionOptionDuration(
					title = stringResource(id = R.string.session_break_duration),
					selected = breakDuration,
					onSessionDurationChange = { duration ->
						onChangeSettings(ChangeSettingsEvent.OnBreakDurationChange(duration))
					}
				)
			}
			item {
				SessionOptionNumber(
					title = stringResource(id = R.string.session_break_duration),
					selected = sessionCountOption,
					onSessionNumberChange = { number ->
						onChangeSettings(ChangeSettingsEvent.OnSessionCountChange(number))
					}
				)
			}
			item {
				Text(
					text = stringResource(id = R.string.session_notification_title),
					style = MaterialTheme.typography.titleLarge,
					color = MaterialTheme.colorScheme.onSurface,
				)
			}
			item {
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