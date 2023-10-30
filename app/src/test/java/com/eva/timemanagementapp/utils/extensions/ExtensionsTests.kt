package com.eva.timemanagementapp.utils.extensions

import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ExtensionsTests {

	@Test
	fun `test checking if two decimal format extension is working`() {

		val numberToCheck = 1.234f.toTwoDecimalFormat()

		assertEquals(
			expected = 1.23f,
			actual = numberToCheck,
			message = "Converted 1.234 to 1.23"
		)

		assertNotEquals(
			illegal = 1.234f,
			actual = numberToCheck,
			message = "Conversion 1.234 to 1.23 should fail"
		)
	}

	@Test
	fun `test to check if LocalTime to millis working`() {

		val someTime = LocalTime.of(0, 0, 2).toMillisOfDay()

		assertEquals(
			expected = 2000L,
			actual = someTime,
			message = "Converted Local time object to millis",
		)

		assertNotEquals(
			illegal = 22_000L,
			actual = someTime,
			message = "Converted Local Time to millis should fail"
		)
	}


	@Test
	fun `test out localtime to HHMMSS and HHMM format`() {
		val someTime = LocalTime.of(12, 30, 20)

		assertEquals(
			expected = "12:30:20",
			actual = someTime.toHMSFormat(),
			message = "Converted Localtime to HMS format",
		)

		assertEquals(
			expected = "12:30",
			actual = someTime.toHMFormat(),
			message = "Converted Localtime to HM format",
		)

		assertNotEquals(
			illegal = "12:00",
			actual = someTime.toHMSFormat(),
			message = "Converted Localtime to HMS format",
		)

		assertNotEquals(
			illegal = "12:30:3",
			actual = someTime.toHMFormat(),
			message = "Converted Localtime to HM format",
		)
	}

	@Test
	fun `check if LocalDate can be converted into a readable week day`() {
		val someDate = LocalDate.of(2023, 10, 25)

		assertEquals(
			expected = "Wed",
			actual = someDate.toReadableWeekday(),
			message = "Today should be tuesday"
		)

		assertNotEquals(
			illegal = "Mon",
			actual = someDate.toReadableWeekday(),
			message = "Today should be tuesday not monday"
		)
	}

	@Test
	fun `check conversion of degree to radians`() {
		val angleInDegree = 90f
		val angleInRadians = (PI / 2).toFloat()

		assertEquals(
			expected = angleInRadians,
			actual = angleInDegree.toRadians(),
			message = "Converted degrees to radians"
		)

		assertNotEquals(
			illegal = PI.toFloat(),
			actual = angleInDegree.toRadians(),
			message = "90 degrees is not PI radians"
		)
	}

}