package com.eva.timemanagementapp.utils.extensions

import java.time.LocalTime

fun LocalTime.toMillisOfDay(): Long {
	val millisInHour = hour * 60 * 60 * 1000L
	val millisInMinutes = minute * 60 * 1000L
	val millisInSeconds = second * 1000L
	return millisInMinutes + millisInSeconds + millisInHour
}