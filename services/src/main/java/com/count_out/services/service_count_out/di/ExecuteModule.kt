package com.count_out.services.service_count_out.di

import android.content.Context
import com.count_out.services.service_count_out.models.RunWorkOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExecuteModule {
    @Singleton
    @Provides
    fun provideRunWorkOut(speechManager: SpeechManager, appContext: Context): RunWorkOut {
        return RunWorkOut(speechManager, appContext)
    }
}