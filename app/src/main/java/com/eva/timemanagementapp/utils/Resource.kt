package com.eva.timemanagementapp.utils

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {

	data class Success<T>(val content: T, val extras: String? = null) :
		Resource<T>(data = content, message = extras)

	data class Error<T>(val errorMessage: String) :
		Resource<T>(data = null, message = errorMessage)
}
