package com.eva.timemanagementapp.domain.repository

import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.utils.Resource
import java.time.LocalDate

interface TimerServiceRepository {

	suspend fun addTimerSession(
		sessionDate: LocalDate = LocalDate.now(),
		option: DurationOption,
		mode: TimerModes
	): Resource<Boolean>

}