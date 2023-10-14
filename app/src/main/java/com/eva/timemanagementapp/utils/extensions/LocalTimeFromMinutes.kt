package com.eva.timemanagementapp.utils.extensions

import java.time.LocalTime

fun Int.toLocalTime(): LocalTime = LocalTime.of(0, this, 0)