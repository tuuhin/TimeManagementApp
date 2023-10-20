package com.eva.timemanagementapp.data.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eva.timemanagementapp.R
import com.eva.timemanagementapp.data.room.convertors.DateConvertor
import com.eva.timemanagementapp.data.room.convertors.DurationConvertor
import com.eva.timemanagementapp.data.room.convertors.TimeConvertor
import com.eva.timemanagementapp.data.room.convertors.TimerModeConvertor
import com.eva.timemanagementapp.data.room.dao.DaySessionDao
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.data.room.entity.DaySessionEntry
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity


@Database(
	version = 2,
	entities = [
		SessionInfoEntity::class,
		DaySessionEntry::class
	],
	autoMigrations = [
		AutoMigration(
			from = 1,
			to = 2,
			spec = AppMigrations.RenameSessionDurationField::class
		)
	],
	exportSchema = true,
)
@TypeConverters(
	DateConvertor::class,
	DurationConvertor::class,
	TimeConvertor::class,
	TimerModeConvertor::class
)
abstract class AppDataBase : RoomDatabase() {

	abstract val sessionDao: SessionInfoDao

	abstract val dailySession: DaySessionDao

	companion object {
		fun getInstance(context: Context): AppDataBase =
			Room.databaseBuilder(
				context = context,
				klass = AppDataBase::class.java,
				name = context.getString(R.string.database_name)
			)
				.addTypeConverter(DateConvertor())
				.addTypeConverter(TimerModeConvertor())
				.addTypeConverter(DurationConvertor())
				.addTypeConverter(TimeConvertor())
				.build()
	}
}