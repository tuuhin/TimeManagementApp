package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.presentation.utils.PreviewFakes
import com.eva.timemanagementapp.presentation.utils.ShowContent
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun StatisticsGraphContainer(
	showLoading: Boolean,
	content: List<SessionReportModel>?,
	modifier: Modifier = Modifier,
	shape: Shape = MaterialTheme.shapes.extraSmall,
	containerColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
	cardElevation: Dp = 0.dp,
	borderStroke: BorderStroke? = null,
	lineWidth: Float = 20f,
	lineColor: Color = MaterialTheme.colorScheme.secondary,
	onLineColor: Color = MaterialTheme.colorScheme.onSecondary,
	axisLineColor: Color = MaterialTheme.colorScheme.outline,
	axisLineWidth: Float = 4f,
	axisTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
	axisTextColor: Color = MaterialTheme.colorScheme.onSurface,
	secondaryAxisStyle: TextStyle = MaterialTheme.typography.bodySmall,
	secondaryAxisColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
	Card(
		modifier = modifier,
		colors = CardDefaults.cardColors(containerColor = containerColor),
		border = borderStroke,
		elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
		shape = shape,
	) {
		when {
			showLoading -> Box(
				modifier = Modifier
					.padding(16.dp)
					.fillMaxSize(),
			) {
				CircularProgressIndicator(
					strokeCap = StrokeCap.Round,
					modifier = Modifier.align(Alignment.Center)
				)
				Text(
					text = stringResource(id = R.string.statistics_loading),
					style = MaterialTheme.typography.titleMedium,
					color = axisLineColor,
					modifier = Modifier.align(Alignment.BottomCenter)
				)
			}

			else -> content?.let { listOfReport ->
				StatisticsGraph(
					content = listOfReport,
					lineWidth = lineWidth,
					lineColor = lineColor,
					axisLineColor = axisLineColor,
					axisLineWidth = axisLineWidth,
					axisTextColor = axisTextColor,
					secondaryAxisColor = secondaryAxisColor,
					secondaryAxisStyle = secondaryAxisStyle,
					axisTextStyle = axisTextStyle,
					onLineColor = onLineColor,
					modifier = Modifier.fillMaxSize()
				)
			}
		}
	}
}

class StatisticsPreviewContent :
	CollectionPreviewParameterProvider<ShowContent<List<SessionReportModel>>>(
		listOf(
			ShowContent(isLoading = true, content = PreviewFakes.FAKE_SESSION_REPORT_WEEKLY),
			ShowContent(isLoading = false, content = PreviewFakes.FAKE_SESSION_REPORT_WEEKLY)
		)
	)

@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatisticsGraphContainerPreview(
	@PreviewParameter(StatisticsPreviewContent::class)
	content: ShowContent<List<SessionReportModel>>
) = TimeManagementAppTheme {
	StatisticsGraphContainer(
		showLoading = content.isLoading,
		content = content.content,
		modifier = Modifier.aspectRatio(1f)
	)
}