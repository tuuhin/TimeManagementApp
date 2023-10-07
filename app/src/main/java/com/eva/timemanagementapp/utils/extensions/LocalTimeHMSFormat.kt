package com.eva.timemanagementapp.utils.extensions

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.toHMSFormat(): String = this.format(DateTimeFormatter.ofPattern("HH:mm:ss"))