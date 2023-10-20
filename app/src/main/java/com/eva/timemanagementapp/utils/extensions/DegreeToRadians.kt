package com.eva.timemanagementapp.utils.extensions

fun Float.toRadians(): Float = (Math.PI / 180f).toFloat() * this.coerceAtLeast(0f)