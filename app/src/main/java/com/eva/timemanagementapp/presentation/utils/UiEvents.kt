package com.eva.timemanagementapp.presentation.utils

sealed class UiEvents {
	data class ShowSnackBar(val message: String) : UiEvents()
}
