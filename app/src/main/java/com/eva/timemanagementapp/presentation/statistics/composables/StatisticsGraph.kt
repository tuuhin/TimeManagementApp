package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.SessionReportModel
import com.eva.timemanagementapp.presentation.utils.PreviewFakes
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import com.eva.timemanagementapp.utils.extensions.toDayOfMonthFormatted
import com.eva.timemanagementapp.utils.extensions.toReadableWeekday
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun StatisticsGraph(
	content: List<SessionReportModel>,
	modifier: Modifier = Modifier,
	lineWidth: Float = 12f,
	axisLineWidth: Float = 4f,
	lineColor: Color = MaterialTheme.colorScheme.secondary,
	onLineColor: Color = MaterialTheme.colorScheme.onSecondary,
	axisLineColor: Color = MaterialTheme.colorScheme.outline,
	axisTextColor: Color = MaterialTheme.colorScheme.onSurface,
	secondaryAxisColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	axisTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
	secondaryAxisStyle: TextStyle = MaterialTheme.typography.bodySmall,
	context: Context = LocalContext.current
) {

	val textMeasurer = rememberTextMeasurer()

	val numberFormatter = remember { NumberFormat.getInstance() }

	Box(
		modifier = modifier
			.padding(16.dp)
			.drawWithCache {
				val today = LocalDate.now()

				val randomWeekDay = today.toReadableWeekday()
				val randomDay = today.toDayOfMonthFormatted()

				val wDayLayout = textMeasurer.measure(
					text = randomWeekDay,
					style = axisTextStyle
				)

				val mDayLayout = textMeasurer.measure(
					text = randomDay,
					style = secondaryAxisStyle
				)

				val wDayTextSize = wDayLayout.size
				val mDayTextSize = mDayLayout.size

				val xAxisHeight = wDayTextSize.height + mDayTextSize.height
				val graphHeight = size.height - xAxisHeight

				val paddingHeight = 8.dp.toPx()
				val paddingWidth = 8.dp.toPx()

				val wDayBaseOffsetX = wDayTextSize.width + paddingWidth
				val mDayBaseOffsetX = mDayTextSize.width + paddingWidth

				val xAxisHeightWithPadding = xAxisHeight + paddingHeight

				onDrawBehind {

					val maxCount = content.maxOf { it.sessionCount }

					val formattedMaxCount = numberFormatter.format(maxCount)

					//draw max
					val measureMaxCountLayout = textMeasurer.measure(
						text = formattedMaxCount,
						style = axisTextStyle
					)
					val maxCountLayoutSize = measureMaxCountLayout.size

					//y axis
					drawLine(
						color = axisLineColor,
						start = Offset(
							x = maxCountLayoutSize.width + paddingWidth,
							y = 0f
						),
						end = Offset(
							x = maxCountLayoutSize.width + paddingWidth,
							y = graphHeight
						),
						strokeWidth = axisLineWidth,
						cap = StrokeCap.Round,
					)
					//x axis
					drawLine(
						color = axisLineColor,
						start = Offset(
							x = maxCountLayoutSize.width + paddingWidth,
							y = graphHeight
						),
						end = Offset(x = size.width, y = graphHeight),
						strokeWidth = axisLineWidth,
						cap = StrokeCap.Round
					)

					val eachBlockWidth = (size.width - wDayTextSize.width) / content.size

					//draw zero
					val formattedZero = numberFormatter.format(0)

					drawText(
						textMeasurer = textMeasurer,
						text = formattedZero,
						style = axisTextStyle.copy(color = axisTextColor),
						topLeft = Offset(
							x = 0f,
							y = size.height - (xAxisHeightWithPadding + measureMaxCountLayout.size.height * .5f)
						)
					)
					//draw max
					if (maxCount != 0) {
						drawText(
							textMeasurer = textMeasurer,
							text = formattedMaxCount,
							style = axisTextStyle.copy(color = axisTextColor),
							topLeft = Offset(
								x = 0f,
								y = measureMaxCountLayout.size.height * .5f
							)
						)
					}

					content.forEachIndexed { idx, report ->
						//weekday
						val weekDay = report.date.toReadableWeekday()
						val dayOfMonth = report.date.toDayOfMonthFormatted()

						val weekDayTextPos = Offset(
							wDayBaseOffsetX + idx * eachBlockWidth,
							size.height - xAxisHeight
						)

						drawText(
							textMeasurer = textMeasurer,
							text = weekDay,
							style = axisTextStyle.copy(color = axisTextColor),
							topLeft = weekDayTextPos,
						)

						val dayOfMonthX = (wDayTextSize.width + mDayTextSize.width) * .5f

						// day of month
						val dayOfMonthTextPos = Offset(
							idx * eachBlockWidth + (mDayBaseOffsetX + dayOfMonthX),
							size.height - wDayTextSize.height
						)

						drawText(
							textMeasurer = textMeasurer,
							text = dayOfMonth,
							style = secondaryAxisStyle.copy(color = secondaryAxisColor),
							topLeft = dayOfMonthTextPos,
						)
					}

					if (maxCount == 0) {
						val noDataText = context.getString(R.string.statistics_no_data)
						val noDataLayoutResults =
							textMeasurer.measure(
								text = noDataText,
								style = axisTextStyle.copy(color = lineColor)
							)

						val textSizeOffset = Offset(
							noDataLayoutResults.size.width / 2f,
							noDataLayoutResults.size.height / 2f
						)

						drawText(
							textLayoutResult = noDataLayoutResults,
							topLeft = center - textSizeOffset,
						)

						return@onDrawBehind
					}

					content.forEachIndexed { idx, report ->
						val ratioDivider = maxCount.coerceAtLeast(1)

						val ratio = (maxCount - report.sessionCount).toFloat() / ratioDivider

						val lineEndY = size.height - xAxisHeightWithPadding
						val lineStartY = ratio * lineEndY

						val lineStartX =
							(wDayTextSize.width * 1.5f) + idx * eachBlockWidth + paddingWidth

						val lineStartOffset = Offset(x = lineStartX, y = lineStartY)

						val lineEndOffset = Offset(x = lineStartX, y = lineEndY)
						//drawing line if count is not zero

						val textColor = if (report.sessionCount == 0) lineColor else onLineColor

						val formattedSessionCount = numberFormatter.format(report.sessionCount)

						val textLayoutResults = textMeasurer.measure(
							text = formattedSessionCount,
							style = axisTextStyle.copy(color = textColor)
						)
						if (report.sessionCount != 0) {
							drawLine(
								color = lineColor,
								start = lineStartOffset,
								end = lineEndOffset,
								strokeWidth = lineWidth + textLayoutResults.size.width * .5f,
								cap = StrokeCap.Round
							)
						}

						val textCenterOffset = Offset(
							textLayoutResults.size.width / 2f,
							textLayoutResults.size.height / 2f
						)
						val excessPadding = if (report.sessionCount == 0)
							Offset.Zero else Offset(0f, 2.dp.toPx())

						drawText(
							textLayoutResult = textLayoutResults,
							topLeft = lineStartOffset - textCenterOffset + excessPadding
						)

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
@Preview(locale = "bn")
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