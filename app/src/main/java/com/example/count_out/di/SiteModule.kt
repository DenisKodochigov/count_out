package com.example.count_out.di

import android.content.Context
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service_count_out.location.Site
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SiteModule {

    @Singleton
    @Provides
    fun provideSite(@ApplicationContext appContext: Context, permissionApp: PermissionApp,): Site {
        return Site(appContext, permissionApp)
    }
}