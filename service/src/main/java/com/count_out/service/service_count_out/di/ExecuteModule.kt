package com.count_out.service.service_count_out.di

import android.content.Context
import com.count_out.framework.text_to_speech.SpeechManager
import com.count_out.service.service_count_out.models.CountOutServiceBind
import com.count_out.service.service_count_out.models.RunWorkOut
import com.count_out.service.service_count_out.models.Work
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
    fun provideSpeechManager(@ApplicationContext appContext: Context): SpeechManager =
        SpeechManager(appContext)

    @Singleton
    @Provides
    fun provideRunWorkOut(
        speechManager: SpeechManager,
        @ApplicationContext appContext: Context,
    ): RunWorkOut {
        return RunWorkOut(speechManager, appContext)
    }

    @Singleton
    @Provides
    fun provideWork(speechManager: SpeechManager, runWorkOut: RunWorkOut): Work {
        return Work(speechManager, runWorkOut)
    }

    @Singleton
    @Provides
    fun provideCountOutServiceBind(@ApplicationContext context: Context): CountOutServiceBind {
        return CountOutServiceBind(context)
    }
}