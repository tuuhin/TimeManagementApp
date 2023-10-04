package com.eva.timemanagementapp.domain.models

enum class SessionNumberOption(val number: Int) {
	ONE_TIME(1),
	TWO_TIMES(2),
	THREE_TIMES(3),
	FOUR_TIMES(4),
	FIVE_TIMES(5);

	companion object {
		fun fromNumber(number: Int): SessionNumberOption = when (number) {
			ONE_TIME.number -> ONE_TIME
			TWO_TIMES.number -> TWO_TIMES
			THREE_TIMES.number -> THREE_TIMES
			FOUR_TIMES.number -> FOUR_TIMES
			else -> FIVE_TIMES
		}
	}
}