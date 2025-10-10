package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.count_out.data.models.Data
import com.count_out.data.models.SettingsDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.BooleanDb
import com.count_out.data.models.types_data.StringDb
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.local.SettingsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SettingsSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>): SettingsSource, PrimeSource() {

    internal val keySpeechDescr = booleanPreferencesKey("speech_description")
    internal val keyAddress = stringPreferencesKey("address_ble_device")
    internal val keyName = stringPreferencesKey("name_ble_device")

    override fun getSettings(): Flow<ResultData<Data>> {
        return merge(getSettingSpeechDescr(), getBleAddress(), getBleName()) }

    override fun getSettingSpeechDescr(): Flow<ResultData<Data>> =
        dataStore.data.map{ ResultData.Success(BooleanDb(it[keySpeechDescr] == true))}

    override fun getBleName(): Flow<ResultData<Data>> =
        dataStore.data.map { ResultData.Success(StringDb(it[keyName] ?: ""))}

    override fun getBleAddress(): Flow<ResultData<Data>> =
        dataStore.data.map { ResultData.Success(StringDb(it[keyAddress] ?: ""))}

    override fun saveSettingSpeechDescr(settings: Data): Flow<ResultData<Data>> =
        saveSetting(settings) { prefs, item->
            prefs[keySpeechDescr] = (item as SettingsDb.SpeechDescription).item }

    override fun saveBleAddress(settings: Data): Flow<ResultData<Data>> =
        saveSetting(settings) {prefs, item->
            prefs[keyAddress] = (item as SettingsDb.AddressBle).item }

    override fun saveBleName(settings: Data): Flow<ResultData<Data>> =
        saveSetting(settings) {prefs, item->
            prefs[keyName] = (item  as SettingsDb.NameBle).item }

    ///############################################################################################
    private fun saveSetting(settings: Data, extractValue: (MutablePreferences, SettingsDb) ->Unit
    ): Flow<ResultData<Data>> =
        flow {emit(
            if (settings is SettingsDb) {
                try {
                    dataStore.edit { prefs -> extractValue(prefs, settings)}
                    ResultData.Success(BooleanDb(true))
                } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
            } else { ResultData.Error(ThrowableDS.NotValidType()) }
        )
    }
}
