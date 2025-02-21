package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.count_out.data.source.local.BleDeviceStoredSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BleDeviceStoredSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val keyAddress:  Preferences. Key<String>,
    private val keyName:  Preferences. Key<String>
): BleDeviceStoredSource {
    override fun getBleAddress(): Flow<String> = dataStore.data.map { it[keyAddress] ?: ""}

    override fun getBleName(): Flow<String> = dataStore.data.map { it[keyName] ?: ""}

    override suspend fun saveBleName(settings: String) {
        dataStore.edit { it[keyName] = settings } }

    override suspend fun saveBleAddress(settings: String) {
        dataStore.edit { it[keyAddress] = settings } }
}