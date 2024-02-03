package com.example.count_out.di

import android.content.Context
import com.example.count_out.ui.joint.NotificationApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun provideNotificationApp(@ApplicationContext appContext: Context): NotificationApp {
        return NotificationApp( appContext )
    }
}