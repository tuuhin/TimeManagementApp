package com.eva.timemanagementapp.data.room.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalTime

@ProvidedTypeConverter
class TimeConvertor {

	@TypeConverter
	fun toTime(time: Long): LocalTime = LocalTime.ofSecondOfDay(time)

	@TypeConverter
	fun fromTime(time: LocalTime): Long = time.toSecondOfDay().toLong()
}