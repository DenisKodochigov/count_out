package com.count_out.device.di

import com.count_out.data.source.framework.LocationSource
import com.count_out.device.location.LocationSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModuleBind {
    @Binds
    abstract fun bindLocationSource(locationSource: LocationSourceImpl): LocationSource
}