package com.eva.timemanagementapp.utils.extensions

import java.text.DecimalFormat

fun Float.toTwoDecimalFormat(): Float = DecimalFormat(".##").format(this).toFloat()