package com.eva.timemanagementapp.di

import android.content.Context
import com.eva.timemanagementapp.data.repository.TimerServiceRepoImpl
import com.eva.timemanagementapp.data.room.AppDataBase
import com.eva.timemanagementapp.data.services.NotificationBuilderHelper
import com.eva.timemanagementapp.domain.repository.TimerServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object TimerServiceModule {

	@Provides
	@ServiceScoped
	fun providesNotificationHelper(
		@ApplicationContext context: Context
	): NotificationBuilderHelper = NotificationBuilderHelper(context)


	@Provides
	@ServiceScoped
	fun providesSessionServiceRepo(
		dataBase: AppDataBase
	): TimerServiceRepository = TimerServiceRepoImpl(
		dailySessionDao = dataBase.dailySession,
		sessionDao = dataBase.sessionDao
	)
}