package com.eva.timemanagementapp.di

import android.content.Context
import com.eva.timemanagementapp.data.services.NotificationBuilderHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object SessionTimerModule {

	@Provides
	@ServiceScoped
	fun providesNotificationHelper(
		@ApplicationContext context: Context
	): NotificationBuilderHelper = NotificationBuilderHelper(context)


}