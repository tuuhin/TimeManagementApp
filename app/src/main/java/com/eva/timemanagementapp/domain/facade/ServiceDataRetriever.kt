package com.eva.timemanagementapp.domain.facade

import kotlinx.coroutines.flow.Flow

interface ServiceDataRetriever {

	val initialValue: Boolean

	val serviceStatus: Flow<Boolean>

}