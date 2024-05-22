package com.example.count_out.di

import android.content.Context
import com.example.count_out.service.bluetooth.BluetoothApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BluetoothModule {
    @Singleton
    @Provides
    fun provideBluetoothManager(@ApplicationContext context: Context): BluetoothApp {
        return BluetoothApp(context)
    }
}