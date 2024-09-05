package com.example.count_out.di

import android.content.Context
import com.example.count_out.entity.MessageApp
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