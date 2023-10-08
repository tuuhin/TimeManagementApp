package com.eva.timemanagementapp.di

import android.content.Context
import com.eva.timemanagementapp.data.services.UIServiceController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ControllerModule {

	@Provides
	@ViewModelScoped
	fun providesServiceIntents(
		@ApplicationContext context: Context
	): UIServiceController = UIServiceController(context)

}