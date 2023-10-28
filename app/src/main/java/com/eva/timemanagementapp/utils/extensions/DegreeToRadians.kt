package com.eva.timemanagementapp.utils.extensions

fun Float.toRadians(): Float {
	return Math.toRadians(this.toDouble()).toFloat()
}
