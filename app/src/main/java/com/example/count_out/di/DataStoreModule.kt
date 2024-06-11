package com.example.count_out.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.count_out.data.datastore.BleDevSerializer
import com.example.count_out.entity.BleDev
import com.example.count_out.entity.Const.DATA_STORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<BleDev> {
        return DataStoreFactory.create(
            serializer = BleDevSerializer,
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { appContext.dataStoreFile( DATA_STORE_FILE_NAME )}
        )
    }
}