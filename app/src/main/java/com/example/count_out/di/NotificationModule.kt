package com.example.count_out.di

import android.content.Context
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.stopwatch.StopWatch
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
    @Singleton
    @Provides
    fun provideStopWatch(): StopWatch {
        return StopWatch()
    }
}