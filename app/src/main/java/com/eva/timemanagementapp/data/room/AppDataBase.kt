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
import com.eva.timemanagementapp.data.room.convertors.TimerModeConvertor
import com.eva.timemanagementapp.data.room.dao.DaySessionDao
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.data.room.entity.DaySessionEntry
import com.eva.timemanagementapp.data.room.entity.SessionInfoEntity


@Database(
	version = 3,
	entities = [
		DaySessionEntry::class,
		SessionInfoEntity::class,
	],
	autoMigrations = [
		AutoMigration(
			from = 1,
			to = 2,
			spec = AppMigrations.RenameSessionDurationField::class
		),
		AutoMigration(
			from = 2,
			to = 3,
			spec = AppMigrations.DeleteSessionAtField::class
		)
	],
	exportSchema = true,
)
@TypeConverters(
	DateConvertor::class,
	DurationConvertor::class,
	TimerModeConvertor::class
)
abstract class AppDataBase : RoomDatabase() {

	abstract fun sessionDao(): SessionInfoDao

	abstract fun dailySession(): DaySessionDao

	companion object {
		fun getInstance(context: Context): AppDataBase =
			Room.databaseBuilder(
				context = context,
				klass = AppDataBase::class.java,
				name = context.getString(R.string.database_name)
			)
				// comment out the to check the fake_data for statistical graph
				//.createFromAsset("database/fake_data.db")
				.addTypeConverter(DateConvertor())
				.addTypeConverter(TimerModeConvertor())
				.addTypeConverter(DurationConvertor())
				.build()
	}
}