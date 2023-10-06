package com.eva.timemanagementapp.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.isoFormatted(): String = format(DateTimeFormatter.ISO_TIME)