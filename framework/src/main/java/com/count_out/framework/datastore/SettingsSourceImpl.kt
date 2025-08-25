package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.count_out.data.models.SettingsImpl
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>): SettingsSource {

    internal val keySpeechDescr = booleanPreferencesKey("speech_description")
    internal val keyAddress = stringPreferencesKey("address_ble_device")
    internal val keyName = stringPreferencesKey("name_ble_device")

    override fun getSettings(): Flow<ResultSource<TypeSource>> {
        return combine(
            getSettingSpeechDescr(),
            getBleAddress(),
            getBleName()
        ) { speechDescription, address, name ->
            ResultSource.Success(TypeSource.SettingsT(SettingsImpl(
                speechDescription = speechDescription.resultBoolean(),
                addressBle = address.resultString(),
                nameBle = name.resultString())))

        }
    }

    override fun getSettingSpeechDescr(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map{ ResultSource.Success(TypeSource.BooleanT(it[keySpeechDescr] == true))}

    override fun getBleName(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map { ResultSource.Success(TypeSource.StringT(it[keyName] ?: ""))}

    override fun getBleAddress(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map { ResultSource.Success(TypeSource.StringT(it[keyAddress] ?: ""))}

    override fun saveSettingSpeechDescr(settings: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(
            try {
                if (settings is TypeSource.SettingT && settings.item is Setting.SpeechDescription) {
                    dataStore.edit {
                        it[keySpeechDescr] = (settings.item as Setting.SpeechDescription).value }
                    ResultSource.Success(TypeSource.BooleanT(true))
                } else ResultSource.Error(ThrowableDS.NotValidType())
            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(t = e)) })
        }
    }

    override fun saveBleAddress(settings: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(
            try {
                if (settings is TypeSource.SettingT && settings.item is Setting.BleAddress) {
//                    Log.d("KDS", "saveBleAddress ${settings.item}")
                    dataStore.edit { it[keyAddress] = (settings.item as Setting.BleAddress).value }
                    ResultSource.Success(TypeSource.BooleanT(true))
                } else ResultSource.Error(ThrowableDS.NotValidType())
            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(t = e)) })
        }
    }

    override fun saveBleName(settings: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(try {
                if (settings is TypeSource.SettingT && settings.item is Setting.BleName) {
//                    Log.d("KDS", "saveBleName ${settings.item}")
                    dataStore.edit { it[keyName] = (settings.item as Setting.BleName).value }
                    ResultSource.Success(TypeSource.BooleanT(true))
                } else ResultSource.Error(ThrowableDS.NotValidType())
            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(t = e)) })
        }
    }
}
