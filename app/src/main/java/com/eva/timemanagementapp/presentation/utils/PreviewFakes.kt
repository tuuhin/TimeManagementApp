package com.eva.timemanagementapp.presentation.utils

import com.eva.timemanagementapp.domain.models.SessionHighlightModel
import com.eva.timemanagementapp.domain.models.SessionReportModel
import java.time.LocalDate

object PreviewFakes {
	val FAKE_HIGHLIGHT_MODE = SessionHighlightModel(
		totalBreakCount = 10,
		totalFocusCount = 10,
		avgFocus = 10f,
		avgBreak = 1f
	)

	val FAKE_SESSION_REPORT_WEEKLY = List(7) { idx ->
		SessionReportModel(
			date = LocalDate.of(2023, 10, idx + 1),
			sessionCount = 5 + idx
		)
	}
}