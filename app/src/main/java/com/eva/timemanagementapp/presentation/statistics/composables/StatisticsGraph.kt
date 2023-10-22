package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.presentation.utils.PreviewFakes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import com.eva.timemanagementapp.utils.extensions.toDayOfMonthFormatted
import com.eva.timemanagementapp.utils.extensions.toReadableWeekday
import java.time.LocalDate

@Composable
fun StatisticsGraph(
	content: List<SessionReportModel>,
	modifier: Modifier = Modifier,
	lineWidth: Float = 20f,
	lineColor: Color = MaterialTheme.colorScheme.primary,
	axisLineColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	axisLineWidth: Float = 4f,
	axisTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
	axisTextColor: Color = MaterialTheme.colorScheme.onSurface,
	secondaryAxisStyle: TextStyle = MaterialTheme.typography.bodySmall,
	secondaryAxisColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {

	val textMeasurer = rememberTextMeasurer()

	Box(
		modifier = modifier
			.padding(16.dp)
			.drawWithCache {
				val today = LocalDate.now()

				val randomWeekDay = today.toReadableWeekday()
				val randomDay = today.toDayOfMonthFormatted()

				val weekDayLayoutResults = textMeasurer.measure(
					text = randomWeekDay,
					style = axisTextStyle
				)

				val dayOfMonthLayoutResults = textMeasurer.measure(
					text = randomDay,
					style = secondaryAxisStyle
				)

				val weekDayLayoutSize = weekDayLayoutResults.size
				val monthDayLayoutSize = dayOfMonthLayoutResults.size

				val totalYaxisSize = weekDayLayoutSize.height + monthDayLayoutSize.height
				val paddingWidth = 10.dp.toPx()
				val paddingHeight = 10.dp.toPx()
				val yAxisWeekDayElementOffset = weekDayLayoutSize.width + paddingHeight
				val yAxisDayOfMonthElementOffset = monthDayLayoutSize.width + paddingHeight

				val totalYAxisWithPadding = totalYaxisSize + paddingHeight

				onDrawBehind {
					//y axis
					drawLine(
						color = axisLineColor,
						start = Offset(
							x = weekDayLayoutSize.width.toFloat(),
							y = 0f
						),
						end = Offset(
							x = weekDayLayoutSize.width.toFloat(),
							y = size.height - totalYaxisSize
						),
						strokeWidth = axisLineWidth,
						cap = StrokeCap.Round,
					)
					//x axis
					drawLine(
						color = axisLineColor,
						start = Offset(
							x = weekDayLayoutSize.width.toFloat(),
							y = size.height - totalYaxisSize
						),
						end = Offset(
							x = size.width,
							y = size.height - totalYaxisSize
						),
						strokeWidth = axisLineWidth,
						cap = StrokeCap.Round
					)

					val eachBlockWidth = (size.width - weekDayLayoutSize.width) / content.size

					//draw zero as it will be always present
					val measureZero = textMeasurer.measure(text = "0", style = axisTextStyle)

					drawText(
						textMeasurer = textMeasurer,
						text = "0",
						style = axisTextStyle.copy(color = axisTextColor),
						topLeft = Offset(
							x = measureZero.size.width.toFloat(),
							y = size.height - (totalYaxisSize + measureZero.size.height * .5f)
						)
					)

					val maxCount = content.maxOf { it.sessionCount }

					content.forEachIndexed { idx, report ->
						//weekday
						val weekDay = report.date.toReadableWeekday()
						val dayOfMonth = report.date.toDayOfMonthFormatted()

						val weekDayTextPos = Offset(
							yAxisWeekDayElementOffset + idx * eachBlockWidth,
							size.height - totalYaxisSize
						)

						drawText(
							textMeasurer = textMeasurer,
							text = weekDay,
							style = axisTextStyle.copy(color = axisTextColor),
							topLeft = weekDayTextPos,
						)

						// day of month
						val dayOfMonthTextPos = Offset(
							idx * eachBlockWidth + (yAxisDayOfMonthElementOffset + weekDayLayoutSize.width * .5f),
							size.height - weekDayLayoutSize.height
						)

						drawText(
							textMeasurer = textMeasurer,
							text = dayOfMonth,
							style = secondaryAxisStyle.copy(color = secondaryAxisColor),
							topLeft = dayOfMonthTextPos,
						)

						val ratioDivider = maxCount.coerceAtLeast(1)

						val ratio = (maxCount - report.sessionCount).toFloat() / ratioDivider

						val lineStartHeight = ratio * (size.height - totalYAxisWithPadding)

						val lineStart = Offset(
							x = (weekDayLayoutSize.width * 1.5f) + idx * eachBlockWidth + paddingWidth,
							y = lineStartHeight
						)

						val lineEnd = Offset(
							x = (weekDayLayoutSize.width * 1.5f) + idx * eachBlockWidth + paddingWidth,
							y = size.height - totalYAxisWithPadding
						)

						drawLine(
							color = lineColor,
							start = lineStart,
							end = lineEnd,
							strokeWidth = lineWidth,
							cap = StrokeCap.Round
						)

						if (report.sessionCount != 0) {
							val countTextLayout = textMeasurer.measure(
								text = "${report.sessionCount}",
								style = axisTextStyle.copy(color = axisTextColor),
							)

							val textHeight =
								(ratio * (size.height - totalYAxisWithPadding)) - (countTextLayout.size.height * .5f)

							drawText(
								textMeasurer = textMeasurer,
								text = "${report.sessionCount}",
								style = axisTextStyle.copy(color = axisTextColor),
								topLeft = Offset(
									x = countTextLayout.size.width.toFloat(),
									y = textHeight
								)
							)
						}
					}
				}
			}
	)
}


@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StatisticsGraphPreview() = TimeManagementAppTheme {
	Surface(
		color = MaterialTheme.colorScheme.background
	) {
		StatisticsGraph(
			content = PreviewFakes.FAKE_SESSION_REPORT_WEEKLY,
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1f)
		)
	}
}