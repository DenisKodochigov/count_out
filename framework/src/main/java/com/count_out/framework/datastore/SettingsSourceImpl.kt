package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.count_out.data.models.SettingsDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.local.SettingsSource
import com.count_out.framework.result
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

    override fun getSettings(): Flow<ResultSource<TypeSource>> {
        return merge(getSettingSpeechDescr(), getBleAddress(), getBleName()) }

    override fun getSettingSpeechDescr(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map{ ResultSource.Success(TypeSource.BooleanT(it[keySpeechDescr] == true))}

    override fun getBleName(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map { ResultSource.Success(TypeSource.StringT(it[keyName] ?: ""))}

    override fun getBleAddress(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map { ResultSource.Success(TypeSource.StringT(it[keyAddress] ?: ""))}

    override fun saveSettingSpeechDescr(settings: TypeSource): Flow<ResultSource<TypeSource>> =
        saveSetting(settings) { prefs, item->
            prefs[keySpeechDescr] = (item as SettingsDb.SpeechDescription).item }

    override fun saveBleAddress(settings: TypeSource): Flow<ResultSource<TypeSource>> =
        saveSetting(settings) {prefs, item->
            prefs[keyAddress] = (item as SettingsDb.AddressBle).item }

    override fun saveBleName(settings: TypeSource): Flow<ResultSource<TypeSource>> =
        saveSetting(settings) {prefs, item->
            prefs[keyName] = (item  as SettingsDb.NameBle).item }

    ///############################################################################################
    private fun saveSetting(settings: TypeSource, extractValue: (MutablePreferences, SettingsDb) ->Unit
    ): Flow<ResultSource<TypeSource>> =
        flow {emit(
            if (settings is TypeSource.SettingsT) {
                try {
                    dataStore.edit { prefs -> extractValue(prefs, settings.item)}
                    true.result()
                } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
            } else { ResultSource.Error(ThrowableDS.NotValidType()) }
        )
    }
}
