package com.eva.timemanagementapp.presentation.statistics

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.presentation.statistics.composables.SessionHighlights
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
	totalSessions: Int,
	averageSessions: Int,
	modifier: Modifier = Modifier
) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = stringResource(id = R.string.navigation_route_statistics)) },
			)
		},
		modifier = modifier
	) { scPadding ->
		Column(
			modifier = Modifier
				.padding(scPadding)
				.padding(horizontal = dimensionResource(id = R.dimen.scaffold_padding)),
		) {
			SessionHighlights(
				total = totalSessions,
				average = averageSessions,
				modifier = Modifier.fillMaxWidth()
			)
			// TODO: Need to add the graph here
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun StatisticsScreenPreview() = TimeManagementAppTheme {
	StatisticsScreen(totalSessions = 25, averageSessions = 3)
}