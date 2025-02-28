package com.count_out.device.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PermissionAppModule {
//    @Singleton
//    @Provides
//    fun providePermissionApp(@ApplicationContext appContext: Context): com.count_out.app.permission.PermissionApp {
//        return com.count_out.app.pervission.PermissionApp(appContext)
//    }
}