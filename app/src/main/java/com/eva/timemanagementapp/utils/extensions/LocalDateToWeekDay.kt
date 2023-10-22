package com.eva.timemanagementapp.utils.extensions

import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.toReadableWeekday(): String =
	dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)

fun LocalDate.toDayOfMonthFormatted(): String = dayOfMonth.toString().padStart(2, '0')