package com.count_out.device.di

import android.content.Context
import com.count_out.app.permission.PermissionApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PermissionAppModule {
    @Singleton
    @Provides
    fun providePermissionApp(@ApplicationContext appContext: Context): com.count_out.app.permission.PermissionApp {
        return PermissionApp(appContext)
    }
}