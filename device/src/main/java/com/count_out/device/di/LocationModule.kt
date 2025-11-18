package com.count_out.device.di

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import com.count_out.device.location.LocationWithOutGoogle
import com.count_out.device.location.Site
import com.count_out.device.permission.PermissionApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {


    @Singleton
    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return context.getSystemService(LOCATION_SERVICE) as LocationManager
    }
    @Singleton
    @Provides
    fun provideLocation(locationManager : LocationManager, permission: PermissionApp ): LocationWithOutGoogle =
        LocationWithOutGoogle(locationManager, permission)
    @Singleton
    @Provides
    fun provideSite(@ApplicationContext context: Context): Site = Site(context)
}