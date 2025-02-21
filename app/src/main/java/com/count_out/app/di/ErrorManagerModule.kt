package com.count_out.app.di

import android.content.Context
import com.count_out.app.entity.MessageApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ErrorManagerModule {
    @Singleton
    @Provides
    fun provideErrorManager(@ApplicationContext appContext: Context): MessageApp {
        return MessageApp(appContext)
    }
}