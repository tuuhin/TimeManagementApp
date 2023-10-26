package com.eva.timemanagementapp.presentation.statistics.composables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.window.PopupProperties
import com.eva.timemanagementapp.presentation.statistics.utils.DeleteStatisticsMenuOptions
import com.eva.timemanagementapp.presentation.statistics.utils.StatisticsType
import com.eva.timemanagementapp.ui.theme.TimeManagementAppTheme
import com.eva.timemanagementapp.utils.extensions.toDpOffset

@Composable
fun StatisticsTopBarOptions(
	onOptionSelect: (StatisticsType) -> Unit,
	modifier: Modifier = Modifier
) {

	var isDropDownOpen by remember { mutableStateOf(false) }
	var dropDownOffset by remember { mutableStateOf(DpOffset.Zero) }

	val dropDownOptions = remember {
		listOf(
			DeleteStatisticsMenuOptions.ClearLastWeekData,
			DeleteStatisticsMenuOptions.ClearTodayData,
		)
	}

	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center
	) {
		IconButton(
			onClick = { isDropDownOpen = true },
			colors = IconButtonDefaults
				.iconButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
			modifier = Modifier.pointerInput(Unit) {
				detectTapGestures { offset ->
					dropDownOffset = offset.toDpOffset()
				}
			}
		) {
			Icon(
				imageVector = Icons.Default.MoreVert,
				contentDescription = "Option"
			)
		}
		DropdownMenu(
			expanded = isDropDownOpen,
			onDismissRequest = { isDropDownOpen = false },
			offset = dropDownOffset,
			properties = PopupProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
		) {
			dropDownOptions.forEach { option ->
				DropdownMenuItem(
					text = { Text(text = stringResource(id = option.label)) },
					onClick = {
						onOptionSelect(option.action)
						isDropDownOpen = false
					},
					leadingIcon = {
						option.icon?.let { res ->
							Icon(
								painter = painterResource(id = res),
								contentDescription = null
							)
						}
					},
					colors = MenuDefaults.itemColors(
						textColor = MaterialTheme.colorScheme.onSurfaceVariant,
						leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
					)
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
fun StatisticsTopBarOptionsPreview() = TimeManagementAppTheme {
	Surface(color = MaterialTheme.colorScheme.background) {
		StatisticsTopBarOptions(onOptionSelect = {})
	}
}