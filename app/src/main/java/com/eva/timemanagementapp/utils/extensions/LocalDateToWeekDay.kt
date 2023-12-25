package com.eva.timemanagementapp.utils.extensions

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.toReadableWeekday(): String =
	dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

fun LocalDate.toDayOfMonthFormatted(): String {
	val formatter = NumberFormat.getInstance()
	val zeroInLocale = formatter.format(0).first()
	val dom = formatter.format(dayOfMonth)
	return dom.padStart(2, zeroInLocale)
}