package com.eva.timemanagementapp.presentation.statistics

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.SessionHighlightModel
import com.eva.timemanagementapp.presentation.statistics.composables.SessionHighlights
import com.eva.timemanagementapp.presentation.statistics.composables.StatisticsTabRow
import com.eva.timemanagementapp.presentation.statistics.utils.StatisticsTabs
import com.eva.timemanagementapp.presentation.utils.PreviewFakes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
	selectedTab: StatisticsTabs,
	highlight: SessionHighlightModel,
	onTabIndexChanged: (StatisticsTabs) -> Unit,
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
			StatisticsTabRow(
				selectedIndex = selectedTab.tabIndex,
				onTabChanged = onTabIndexChanged
			)
			Spacer(
				modifier = Modifier
					.height(dimensionResource(id = R.dimen.statistics_item_spacing))
			)
			Text(
				text = stringResource(id = R.string.highlights_text),
				style = MaterialTheme.typography.titleLarge,
				color = MaterialTheme.colorScheme.onSurface
			)
			SessionHighlights(
				highlight = highlight,
				modifier = Modifier.fillMaxWidth()
			)
			Text(
				text = stringResource(id = R.string.highlights_graph),
				style = MaterialTheme.typography.titleLarge,
				color = MaterialTheme.colorScheme.onSurface
			)
			Card(
				shape = MaterialTheme.shapes.medium,
				modifier = Modifier
					.padding(bottom = dimensionResource(id = R.dimen.scaffold_padding))
					.fillMaxSize()
			) {

			}
		}
	}
}

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatisticsScreenPreview() = TimeManagementAppTheme {
	StatisticsScreen(
		selectedTab = StatisticsTabs.All,
		highlight = PreviewFakes.FAKE_HIGHLIGHT_MODE,
		onTabIndexChanged = { },
	)
}