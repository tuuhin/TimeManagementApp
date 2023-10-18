package com.eva.timemanagementapp.data.room.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate

@ProvidedTypeConverter
class DateConvertor {

	@TypeConverter
	fun toDate(date: Long): LocalDate = LocalDate.ofEpochDay(date)

	@TypeConverter
	fun fromDate(date: LocalDate): Long = date.toEpochDay()
}