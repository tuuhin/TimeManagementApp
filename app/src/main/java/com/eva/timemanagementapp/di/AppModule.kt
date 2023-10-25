package com.eva.timemanagementapp.di

import android.content.Context
import com.eva.timemanagementapp.data.datastore.SettingsPreferencesImpl
import com.eva.timemanagementapp.data.repository.AirPlaneSettingsInfo
import com.eva.timemanagementapp.data.repository.SessionReminderAlarm
import com.eva.timemanagementapp.data.room.AppDataBase
import com.eva.timemanagementapp.data.room.dao.SessionInfoDao
import com.eva.timemanagementapp.domain.facade.SessionReminderFacade
import com.eva.timemanagementapp.domain.facade.SettingsInfoFacade
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesAppDataBase(
		@ApplicationContext context: Context
	): AppDataBase = AppDataBase.getInstance(context)

	@Provides
	@Singleton
	fun providesSessionInfoDao(
		dataBase: AppDataBase
	): SessionInfoDao = dataBase.sessionDao()

	@Provides
	@Singleton
	fun provideSettingsPrefs(
		@ApplicationContext context: Context
	): SettingsPreferences = SettingsPreferencesImpl(context)

	@Provides
	@Singleton
	fun providesAirplaneListener(
		@ApplicationContext context: Context
	): SettingsInfoFacade = AirPlaneSettingsInfo(context)

	@Provides
	@Singleton
	fun providesReminderAlarm(
		@ApplicationContext context: Context
	): SessionReminderFacade = SessionReminderAlarm(context)
}