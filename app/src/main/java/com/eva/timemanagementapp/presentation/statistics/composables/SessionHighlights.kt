package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun SessionHighlights(
	total: Int,
	average: Int,
	modifier: Modifier = Modifier
) {
	Row(
		modifier,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Box(
			modifier = Modifier
				.clip(MaterialTheme.shapes.medium)
				.background(MaterialTheme.colorScheme.secondaryContainer)
				.padding(20.dp)
				.aspectRatio(1f)
				.weight(1f)
		) {
			Text(
				text = "$total", style = MaterialTheme.typography.displaySmall,
				color = MaterialTheme.colorScheme.onSecondaryContainer,
				modifier = Modifier.align(Alignment.CenterStart)
			)
			Text(
				text = stringResource(id = R.string.total_session_count),
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSecondaryContainer,
				modifier = Modifier.align(Alignment.BottomStart)
			)
		}
		Spacer(modifier = Modifier.width(20.dp))
		Box(
			modifier = Modifier
				.clip(MaterialTheme.shapes.medium)
				.background(MaterialTheme.colorScheme.tertiaryContainer)
				.padding(20.dp)
				.aspectRatio(1f)
				.weight(1f)
		) {
			Text(
				text = "$average",
				style = MaterialTheme.typography.displaySmall,
				color = MaterialTheme.colorScheme.onTertiaryContainer,
				modifier = Modifier.align(Alignment.CenterStart),
			)

			Text(
				text = stringResource(id = R.string.daily_session_count),
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onTertiaryContainer,
				modifier = Modifier.align(Alignment.BottomStart),
			)
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SessionHighlightPreview() = TimeManagementAppTheme {
	SessionHighlights(
		total = 25,
		average = 5,
		modifier = Modifier.fillMaxWidth(),
	)
}