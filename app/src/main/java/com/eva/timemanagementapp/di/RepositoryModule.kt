package com.eva.timemanagementapp.di

import android.content.Context
import com.eva.timemanagementapp.data.repository.StatisticsRepoImpl
import com.eva.timemanagementapp.data.room.AppDataBase
import com.eva.timemanagementapp.data.services.UIServiceController
import com.eva.timemanagementapp.domain.repository.StatisticsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

	@Provides
	@ViewModelScoped
	fun providesServiceIntents(
		@ApplicationContext context: Context
	): UIServiceController = UIServiceController(context)


	@Provides
	@ViewModelScoped
	fun providesSessionStatisticsRepo(
		dataBase: AppDataBase
	): StatisticsRepository = StatisticsRepoImpl(
		sessionDao = dataBase.sessionDao()
	)

}