package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.count_out.data.models.SettingsImpl
import com.count_out.data.source.local.SettingsSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.count_out.domain.entity.Settings

class SettingsSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>): SettingsSource {
    private val keySpeechDescr = booleanPreferencesKey("speech_description")
    private val keyAddress = stringPreferencesKey("address_ble_device")
    private val keyName = stringPreferencesKey("name_ble_device")

    override fun getSettings(): Flow<Settings> {
        return combine(
            getSettingSpeechDescr(),
            getBleAddress(),
            getBleName()
        ) { speechDescription, address, name ->
            SettingsImpl(
                speechDescription = speechDescription,
                addressBle = address,
                nameBle = name
            )
        }
    }

    override fun getSettingSpeechDescr(): Flow<Boolean> {
        return dataStore.data.map { it[keySpeechDescr] == true } }
    override fun saveSettingSpeechDescr(settings: Boolean) {
        CoroutineScope(Dispatchers.IO).launch { dataStore.edit { it[keySpeechDescr] = settings } }
    }
    override fun getBleAddress(): Flow<String> = dataStore.data.map { it[keyAddress] ?: ""}

    override fun saveBleAddress(settings: String) {
        CoroutineScope(Dispatchers.IO).launch { dataStore.edit { it[keyAddress] = settings }
        } }

    override fun getBleName(): Flow<String> = dataStore.data.map { it[keyName] ?: ""}

    override fun saveBleName(settings: String) {
        CoroutineScope(Dispatchers.IO).launch { dataStore.edit { it[keyName] = settings } }}

}
