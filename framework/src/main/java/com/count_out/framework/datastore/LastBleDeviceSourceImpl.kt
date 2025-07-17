package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.source.SourceData
import com.count_out.data.source.local.LastBleDeviceSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class LastBleDeviceSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): LastBleDeviceSource, SourceData() {
    internal val keyName = stringPreferencesKey("last_device")

    override fun saveDevice(addr: String): Flow<ResultSource<Boolean>> {
        CoroutineScope(Dispatchers.IO).launch { dataStore.edit { it[keyName] = addr } }
        return flow { emit(ResultSource.Success(true)) }
    }

    override fun getDevice(): Flow<ResultSource<String>> {
        return dataStore.data.map { it[keyName] ?: "" }.resultSource()
    }
}
