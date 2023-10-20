package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.SessionHighlightModel
import com.eva.timemanagementapp.presentation.utils.PreviewFakes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun SessionHighlights(
	highlight: SessionHighlightModel,
	modifier: Modifier = Modifier
) {
	LazyVerticalGrid(
		modifier = modifier,
		columns = GridCells.Fixed(2),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		contentPadding = PaddingValues(vertical = 10.dp),
		userScrollEnabled = false
	) {
		item {
			HighLightCard(
				highlight = "${highlight.totalFocusCount}",
				highlightText = stringResource(id = R.string.total_focus_count),
				background = MaterialTheme.colorScheme.primaryContainer,
				contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
				modifier = Modifier.fillMaxWidth()
			)
		}
		item {
			HighLightCard(
				highlight = "${highlight.avgFocus}",
				highlightText = stringResource(id = R.string.average_focus_time),
				highlightUnit = stringResource(id = R.string.minutes_short_hand),
				background = MaterialTheme.colorScheme.primaryContainer,
				contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
				modifier = Modifier.fillMaxWidth()
			)
		}
		item {
			HighLightCard(
				highlight = "${highlight.totalBreakCount}",
				highlightText = stringResource(id = R.string.total_break_count),
				modifier = Modifier.fillMaxWidth()
			)
		}
		item {
			HighLightCard(
				highlight = "${highlight.avgBreak}",
				highlightText = stringResource(id = R.string.average_break_time),
				highlightUnit = stringResource(id = R.string.minutes_short_hand),
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SessionHighlightPreview() = TimeManagementAppTheme {
	SessionHighlights(
		highlight = PreviewFakes.FAKE_HIGHLIGHT_MODE
	)
}