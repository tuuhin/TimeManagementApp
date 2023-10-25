package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun SettingsOptionContainer(
	title: String,
	subtitle: String?,
	modifier: Modifier = Modifier,
	containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	shape: Shape = MaterialTheme.shapes.small,
	titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
	subTitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
	elevation: Dp = 0.dp,
	content: @Composable BoxScope.() -> Unit
) {
	Card(
		modifier = modifier,
		elevation = CardDefaults
			.cardElevation(defaultElevation = elevation),
		colors = CardDefaults.cardColors(
			containerColor = containerColor,
			contentColor = contentColor
		),
		shape = shape,
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(all = dimensionResource(id = R.dimen.settings_card_padding)),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Column(
				modifier = Modifier.weight(.7f),
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(
					text = title,
					style = titleTextStyle
				)
				subtitle?.let {
					Text(
						text = subtitle,
						style = subTitleTextStyle,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)
				}
			}
			Box(
				contentAlignment = Alignment.Center,
				content = content,
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
@Composable
fun SettingsContainerPreview() = TimeManagementAppTheme {
	SettingsOptionContainer(title = "Session Goal", subtitle = null) {
		Text(text = "3")
	}
}