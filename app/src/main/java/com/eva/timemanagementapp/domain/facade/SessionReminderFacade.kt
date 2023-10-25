package com.eva.timemanagementapp.domain.facade

import java.time.LocalTime

interface SessionReminderFacade {

	fun setGoalReminderAlarm(time: LocalTime)

	fun stopAlarm()
}