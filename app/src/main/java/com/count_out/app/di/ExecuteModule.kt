package com.count_out.app.di

import android.content.Context
import com.count_out.app.device.text_to_speech.SpeechManager
import com.count_out.app.services.count_out.RunWorkOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExecuteModule {
    @Singleton
    @Provides
    fun provideRunWorkOut(
        speechManager: SpeechManager,
        @ApplicationContext appContext: Context,
    ): RunWorkOut {
        return RunWorkOut(speechManager, appContext)
    }
}