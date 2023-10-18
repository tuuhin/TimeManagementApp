package com.eva.timemanagementapp.di

import android.content.Context
import com.eva.timemanagementapp.data.repository.SessionsRepositoryImpl
import com.eva.timemanagementapp.data.room.AppDataBase
import com.eva.timemanagementapp.domain.repository.SessionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SessionsRepositoryModule {

	@Provides
	@Singleton
	fun providesAppDataBase(@ApplicationContext context: Context): AppDataBase =
		AppDataBase.getInstance(context)

	@Provides
	@Singleton
	fun providesSessionRepo(dataBase: AppDataBase): SessionsRepository = SessionsRepositoryImpl(
		dailySessionDao = dataBase.dailySession,
		sessionDao = dataBase.sessionDao
	)
}