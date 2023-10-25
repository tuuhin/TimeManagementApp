package com.eva.timemanagementapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.eva.timemanagementapp.domain.facade.SettingsPreferences
import com.eva.timemanagementapp.domain.models.DurationOption
import com.eva.timemanagementapp.domain.models.SessionNumberOption
import com.eva.timemanagementapp.utils.extensions.toHMFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import java.time.format.DateTimeFormatter


private val Context.datastore by preferencesDataStore(name = PreferencesKeys.PREFERENCE_NAME)

class SettingsPreferencesImpl(
	private val context: Context
) : SettingsPreferences {

	private val focusDurationKey = intPreferencesKey(name = PreferencesKeys.FOCUS_DURATION)
	private val breakDurationKey = intPreferencesKey(name = PreferencesKeys.BREAK_DURATION)
	private val sessionCountKey = intPreferencesKey(name = PreferencesKeys.SESSION_COUNT)

	private val isSaveSessionKey = booleanPreferencesKey(name = PreferencesKeys.ALLOW_SAVE_SESSIONS)
	private val reminderTimer = stringPreferencesKey(name = PreferencesKeys.REMINDER_TIME)

	override val focusDuration: Flow<DurationOption>
		get() = context.datastore.data.map { pref ->
			pref[focusDurationKey]?.let(DurationOption::fromNumber)
				?: DurationOption.TEN_MINUTES
		}

	override val breakDuration: Flow<DurationOption>
		get() = context.datastore.data.map { pref ->
			pref[breakDurationKey]?.let(DurationOption::fromNumber)
				?: DurationOption.FIVE_MINUTES
		}

	override val sessionCount: Flow<SessionNumberOption>
		get() = context.datastore.data.map { pref ->
			pref[sessionCountKey]?.let(SessionNumberOption::fromNumber)
				?: SessionNumberOption.TWO_TIMES
		}
	override val isSaveSessions: Flow<Boolean>
		get() = context.datastore.data.map { pref ->
			pref[isSaveSessionKey] ?: false
		}

	override val reminderTime: Flow<LocalTime>
		get() = context.datastore.data.map { pref ->
			pref[reminderTimer]?.toLocalTime() ?: LocalTime.of(0, 0)
		}


	override suspend fun setFocusDuration(duration: DurationOption) {
		context.datastore.edit { pref -> pref[focusDurationKey] = duration.minutes }
	}

	override suspend fun setBreakDuration(duration: DurationOption) {
		context.datastore.edit { pref -> pref[breakDurationKey] = duration.minutes }
	}

	override suspend fun setSessionCount(count: SessionNumberOption) {
		context.datastore.edit { pref -> pref[sessionCountKey] = count.number }
	}

	override suspend fun setIsSaveSessions(allow: Boolean) {
		context.datastore.edit { pref -> pref[isSaveSessionKey] = allow }
	}

	override suspend fun setReminderTime(time: LocalTime) {
		context.datastore.edit { pref -> pref[reminderTimer] = time.toHMFormat() }
	}
}

private fun String.toLocalTime(): LocalTime =
	LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
