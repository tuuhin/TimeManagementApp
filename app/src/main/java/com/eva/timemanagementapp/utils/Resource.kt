package com.eva.timemanagementapp.utils

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {

	data class Success<T>(val content: T) : Resource<T>(data = content, message = null)

	data class Error<T>(val errorMessage: String) : Resource<T>(data = null, message = errorMessage)
}
