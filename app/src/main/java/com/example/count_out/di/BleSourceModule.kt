package com.example.count_out.di

import com.example.count_out.data.source.framework.BleSource
import com.example.count_out.devices.bluetooth.BleSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BleSourceModule {
    @Binds
    abstract fun bindBleSource(source: BleSourceImpl): BleSource

}