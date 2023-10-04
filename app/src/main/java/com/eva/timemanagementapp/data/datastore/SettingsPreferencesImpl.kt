package com.eva.timemanagementapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import com.eva.timemanagementapp.domain.models.SessionDurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.datastore by preferencesDataStore(name = PreferencesKeys.PREFERENCE_NAME)

class SettingsPreferencesImpl(
	private val context: Context
) : SettingsPreferences {

	private val focusDurationKey = intPreferencesKey(name = PreferencesKeys.FOCUS_DURATION)
	private val breakDurationKey = intPreferencesKey(name = PreferencesKeys.BREAK_DURATION)
	private val sessionCountKey = intPreferencesKey(name = PreferencesKeys.SESSION_COUNT)

	override val focusDuration: Flow<SessionDurationOption>
		get() = context.datastore.data.map { pref ->
			pref[focusDurationKey]?.let { number ->
				SessionDurationOption.fromNumber(number)
			} ?: SessionDurationOption.TEN_MINUTES
		}
	override val breakDuration: Flow<SessionDurationOption>
		get() = context.datastore.data.map { pref ->
			pref[breakDurationKey]?.let { number ->
				SessionDurationOption.fromNumber(number)
			} ?: SessionDurationOption.FIVE_MINUTES
		}
	override val sessionCount: Flow<SessionNumberOption>
		get() = context.datastore.data.map { pref ->
			pref[sessionCountKey]?.let { number ->
				SessionNumberOption.fromNumber(number)
			} ?: SessionNumberOption.TWO_TIMES
		}

	override suspend fun setFocusDuration(duration: SessionDurationOption) {
		context.datastore.edit { pref -> pref[focusDurationKey] = duration.minutes }
	}

	override suspend fun setBreakDuration(duration: SessionDurationOption) {
		context.datastore.edit { pref -> pref[breakDurationKey] = duration.minutes }
	}

	override suspend fun setSessionCount(count: SessionNumberOption) {
		context.datastore.edit { pref -> pref[sessionCountKey] = count.number }
	}
}