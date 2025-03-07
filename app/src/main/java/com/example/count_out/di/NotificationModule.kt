package com.example.count_out.di

import android.content.Context
import com.example.count_out.framework.notification.NotificationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {
    @Singleton
    @Provides
    fun provideNotificationApp(@ApplicationContext appContext: Context): NotificationHelper {
        return NotificationHelper( appContext )
    }
}