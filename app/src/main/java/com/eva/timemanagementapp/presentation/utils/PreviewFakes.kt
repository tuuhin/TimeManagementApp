package com.eva.timemanagementapp.presentation.utils

import com.eva.timemanagementapp.domain.models.SessionHighlightModel

object PreviewFakes {
	val FAKE_HIGHLIGHT_MODE = SessionHighlightModel(
		totalBreakCount = 10,
		totalFocusCount = 10,
		avgFocus = 10f,
		avgBreak = 1f
	)
}