package com.eva.timemanagementapp.presentation.settings.composables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.text.NumberFormat

@Composable
fun SessionOptionNumber(
	title: String,
	selected: SessionNumberOption,
	onSessionNumberChange: (SessionNumberOption) -> Unit,
	modifier: Modifier = Modifier,
	containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
	shape: Shape = MaterialTheme.shapes.small,
	elevation: Dp = 0.dp,
) {
	var menuAnchor by remember { mutableStateOf(DpOffset.Zero) }

	var isDropDownVisible by remember { mutableStateOf(false) }

	val subTitleText by remember(selected) {
		derivedStateOf {
			val formatter = NumberFormat.getInstance()
			val number = formatter.format(selected.number)
			number
		}
	}

	SettingsOptionContainer(
		title = title,
		subtitle = subTitleText,
		modifier = modifier,
		containerColor = containerColor,
		contentColor = contentColor,
		shape = shape,
		elevation = elevation,
	) {
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
				val dropDownItemText by remember {
					derivedStateOf {
						val formatter = NumberFormat.getInstance()
						val number = formatter.format(option.number)
						number
					}
				}
				DropdownMenuItem(
					text = { Text(text = dropDownItemText) },
					onClick = { onSessionNumberChange(option) },
					contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.menu_item_padding)),
				)
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
