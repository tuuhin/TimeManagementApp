package com.eva.timemanagementapp.presentation.utils

data class ShowContent<T>(
	val isLoading: Boolean = true,
	val content: T? = null
)
