package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.count_out.data.source.local.SettingsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val key:  Preferences. Key<Boolean>
): SettingsSource {
    override fun getSettingSpeechDescr(): Flow<Boolean> {
        return dataStore.data.map { it[key] ?: false}
    }
    override suspend fun saveSettingSpeechDescr(settings: Boolean) {
        dataStore.edit { it[key] = settings }
    }
}
