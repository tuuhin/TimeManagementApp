package com.eva.timemanagementapp.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import com.eva.timemanagementapp.presentation.settings.composables.otherSettings
import com.eva.timemanagementapp.presentation.settings.composables.setNotificationSettings
import com.eva.timemanagementapp.presentation.settings.composables.setSessionSettings
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
	focusDuration: DurationOption,
	breakDuration: DurationOption,
	sessionCountOption: SessionNumberOption,
	isSaveAllowed: Boolean,
	isAirplaneModeEnabled: Boolean,
	reminderTime: LocalTime,
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
			setSessionSettings(
				focusDuration = focusDuration,
				breakDuration = breakDuration,
				isSaveAllowed = isSaveAllowed,
				onChangeSettings = onChangeSettings
			)
			setNotificationSettings(
				reminderTime = reminderTime,
				sessionCountOption = sessionCountOption,
				onChangeSettings = onChangeSettings
			)
			otherSettings(
				isAirplaneModeEnabled = isAirplaneModeEnabled
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
@Preview
@Composable
fun SettingsScreenPreview() = TimeManagementAppTheme {
	SettingsScreen(
		focusDuration = DurationOption.TEN_MINUTES,
		breakDuration = DurationOption.TEN_MINUTES,
		sessionCountOption = SessionNumberOption.TWO_TIMES,
		isAirplaneModeEnabled = false,
		reminderTime = LocalTime.of(0, 0),
		onChangeSettings = {},
		isSaveAllowed = true
	)
}