package com.example.count_out.di

import android.content.Context
import com.example.count_out.domain.SpeechManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpeechManagerModule {
    @Singleton
    @Provides
    fun provideSpeechManager(@ApplicationContext appContext: Context): SpeechManager {
        val speechManager = SpeechManager(appContext)
        speechManager.init()
        return speechManager
    }
}