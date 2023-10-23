package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun HighLightCard(
	highlight: String,
	highlightText: String,
	modifier: Modifier = Modifier,
	highlightUnit: String? = null,
	highlightStyle: TextStyle = MaterialTheme.typography.headlineMedium,
	highlightTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
	shape: Shape = MaterialTheme.shapes.medium,
	background: Color = MaterialTheme.colorScheme.secondaryContainer,
	contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer
) {
	Card(
		modifier = modifier,
		shape = shape,
		colors = CardDefaults
			.cardColors(containerColor = background, contentColor = contentColor)
	) {
		Column(
			modifier = Modifier.padding(12.dp),
			horizontalAlignment = Alignment.Start,
			verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			Text(
				text = buildAnnotatedString {
					append(highlight)
					highlightUnit?.let { unit ->

						withStyle(
							style = SpanStyle(
								fontSize = MaterialTheme.typography.bodySmall.fontSize,
							)
						) {
							append(" ")
							append(unit)
						}
					}
				},
				style = highlightStyle,
			)
			Text(text = highlightText, style = highlightTextStyle)
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun HighlightCardWithoutUnitPreview() = TimeManagementAppTheme {
	HighLightCard(highlight = "3", highlightText = "Some text")
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun HighlightCardWithUnitPreview() = TimeManagementAppTheme {
	HighLightCard(highlight = "3", highlightText = "Some text", highlightUnit = "min")
}