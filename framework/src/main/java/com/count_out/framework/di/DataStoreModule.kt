package com.count_out.framework.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.count_out.framework.datastore.BleDeviceStoredSourceImpl
import com.count_out.framework.datastore.SettingsSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "count_out")
    private val keySpeechDescr = booleanPreferencesKey("speech_description")

    @Provides
    fun provideSettingsSourceImpl(@ApplicationContext context: Context) =
        SettingsSourceImpl(context.dataStore, keySpeechDescr)

    private val bleAddrKey = stringPreferencesKey("address_ble_device")
    private val bleNameKey = stringPreferencesKey("name_ble_device")
    @Provides
    fun provideBleDeviceStoredSourceImpl(@ApplicationContext context: Context) =
        BleDeviceStoredSourceImpl(context.dataStore, bleAddrKey, bleNameKey)
}