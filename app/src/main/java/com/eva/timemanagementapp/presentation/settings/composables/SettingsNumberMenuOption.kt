package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme

@Composable
fun SessionOptionNumber(
	title: String,
	selected: SessionNumberOption,
	onSessionNumberChange: (SessionNumberOption) -> Unit,
	modifier: Modifier = Modifier,
	contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
	containerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
	shape: Shape = MaterialTheme.shapes.small,
	elevation: Dp = 0.dp,
) {
	var menuAnchor by remember { mutableStateOf(DpOffset.Zero) }

	var isDropDownVisible by remember { mutableStateOf(false) }

	Card(
		modifier = modifier,
		elevation = CardDefaults.cardElevation(defaultElevation = elevation),
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
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(text = title, style = MaterialTheme.typography.titleMedium)
				Text(
					text = "${selected.number}",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
				)
			}
			Box {
				IconButton(
					onClick = { isDropDownVisible = true },
					modifier = Modifier.pointerInput(Unit) {
						detectTapGestures { offset ->
							menuAnchor = DpOffset(offset.x.dp, offset.y.dp)
						}
					},
				) {
					Icon(
						imageVector = Icons.Default.MoreVert,
						contentDescription = stringResource(id = R.string.settings_option_cnt_desc),
					)
				}
				DropdownMenu(
					expanded = isDropDownVisible,
					onDismissRequest = { isDropDownVisible = false },
					properties = PopupProperties(
						dismissOnBackPress = false,
						dismissOnClickOutside = true
					),
					offset = menuAnchor
				) {
					SessionNumberOption.entries.forEach { option ->
						DropdownMenuItem(
							text = { Text(text = "${option.number}") },
							onClick = { onSessionNumberChange(option) },
							contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.menu_item_padding)),
						)
					}
				}
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
fun SessionOptionNumberPreview() = TimeManagementAppTheme {
	SessionOptionNumber(
		title = "Session Goal",
		selected = SessionNumberOption.TWO_TIMES,
		onSessionNumberChange = {},
		modifier = Modifier.fillMaxWidth(),
	)
}
