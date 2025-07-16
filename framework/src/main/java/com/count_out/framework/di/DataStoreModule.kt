package com.count_out.framework.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.count_out.framework.datastore.LastBleDeviceSourceImpl
import com.count_out.framework.datastore.LastPlanSourceImpl
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

    @Provides
    fun provideSettingsSourceImpl(@ApplicationContext context: Context) =
        SettingsSourceImpl(context.dataStore)
    @Provides
    fun provideLastPlanSourceImpl(@ApplicationContext context: Context) =
        LastPlanSourceImpl(context.dataStore)
    @Provides
    fun provideLastBleDeviceSourceImpl(@ApplicationContext context: Context) =
        LastBleDeviceSourceImpl(context.dataStore)
}