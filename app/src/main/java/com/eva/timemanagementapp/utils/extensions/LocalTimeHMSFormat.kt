package com.eva.timemanagementapp.utils.extensions

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.toHMSFormat(): String = format(DateTimeFormatter.ofPattern("HH:mm:ss"))

fun LocalTime.toHMFormat(): String = format(DateTimeFormatter.ofPattern("HH:mm"))

