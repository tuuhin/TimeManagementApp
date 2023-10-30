package com.eva.timemanagementapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
	private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

	override fun starting(description: Description) {
		super.starting(description)
		Dispatchers.setMain(dispatcher)
	}

	override fun finished(description: Description) {
		Dispatchers.resetMain()
		super.finished(description)
	}
}