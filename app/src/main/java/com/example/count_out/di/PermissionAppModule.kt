package com.example.count_out.di

import android.content.Context
import com.example.count_out.ui.permission.PermissionApp
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
    fun providePermissionApp(@ApplicationContext appContext: Context): PermissionApp {
        return PermissionApp(appContext)
    }
}