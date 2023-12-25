package com.eva.timemanagementapp.utils.extensions

import java.text.NumberFormat
import java.time.LocalTime


fun LocalTime.toHMSFormat(): String {
	val numberFormatter = NumberFormat.getInstance()

	val zeroInLocale = numberFormatter.format(0).first()
	val hour = numberFormatter.format(hour)
		.padStart(2, zeroInLocale)
	val minutes = numberFormatter.format(minute)
		.padStart(2, zeroInLocale)
	val seconds = numberFormatter.format(second)
		.padStart(2,zeroInLocale)

	return "$hour:$minutes:$seconds"
}
fun LocalTime.toHMFormat(): String {
	val numberFormatter = NumberFormat.getInstance()
	val zeroInLocale = numberFormatter.format(0).first()
	val hour = numberFormatter.format(hour)
		.padStart(2, zeroInLocale)
	val minutes = numberFormatter.format(minute)
		.padStart(2, zeroInLocale)

	return "$hour:$minutes"
}