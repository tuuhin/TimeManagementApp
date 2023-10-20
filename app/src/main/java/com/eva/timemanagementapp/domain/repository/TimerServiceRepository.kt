package com.eva.timemanagementapp.domain.repository

import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.TimerModes
import com.eva.timemanagementapp.utils.Resource

interface TimerServiceRepository {

	suspend fun addTimerSession(option: DurationOption, mode: TimerModes): Resource<Boolean>

}