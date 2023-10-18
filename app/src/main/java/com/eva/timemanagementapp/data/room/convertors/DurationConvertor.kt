package com.eva.timemanagementapp.data.room.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.eva.timemanagementapp.domain.models.DurationOption

@ProvidedTypeConverter
class DurationConvertor {

	@TypeConverter
	fun toDuration(option: DurationOption): String = option.name

	@TypeConverter
	fun fromDuration(name: String): DurationOption = DurationOption.valueOf(name)
}