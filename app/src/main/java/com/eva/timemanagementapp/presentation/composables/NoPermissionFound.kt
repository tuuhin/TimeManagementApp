package com.eva.timemanagementapp.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.timemanagementapp.R

@Composable
fun NoPermissionsFound(
	checkPerms: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_notification_bell),
			contentDescription = null,
			colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
		)
		Text(
			text = stringResource(id = R.string.no_notification_perms_title),
			style = MaterialTheme.typography.titleLarge
		)
		Text(
			text = stringResource(id = R.string.no_notification_perms_text),
			style = MaterialTheme.typography.bodyLarge
		)
		Spacer(modifier = Modifier.height(16.dp))
		Button(
			onClick = checkPerms,
			colors = ButtonDefaults.buttonColors(
				containerColor = MaterialTheme.colorScheme.secondaryContainer,
				contentColor = MaterialTheme.colorScheme.onSecondaryContainer
			)
		) {
			Text(text = stringResource(id = R.string.request_permission_button_text))
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
fun NoPermissionsFoundPreview() {
	NoPermissionsFound(checkPerms = {})
}