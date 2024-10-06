package com.example.count_out.di

import android.content.Context
import com.example.count_out.domain.SpeechManager
import com.example.count_out.service_count_out.work.execute.ExecuteExercise
import com.example.count_out.service_count_out.work.execute.ExecuteRound
import com.example.count_out.service_count_out.work.execute.ExecuteSet
import com.example.count_out.service_count_out.work.execute.ExecuteWork
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
    fun provideExecuteWorkOut(
        speechManager: SpeechManager,
        executeRound: ExecuteRound,
    ): ExecuteWork {
        return ExecuteWork(speechManager, executeRound)
    }

    @Singleton
    @Provides
    fun provideExecuteRound(
        speechManager: SpeechManager,
        executeExercise: ExecuteExercise,
        @ApplicationContext appContext: Context,
    ): ExecuteRound {
        return ExecuteRound(speechManager, executeExercise, appContext)
    }

    @Singleton
    @Provides
    fun provideExecuteExercise(
        speechManager: SpeechManager,
        executeSet: ExecuteSet,
        @ApplicationContext appContext: Context,
    ): ExecuteExercise {
        return ExecuteExercise(speechManager, executeSet, appContext)
    }

    @Singleton
    @Provides
    fun provideExecuteSet(
        speechManager: SpeechManager,
        @ApplicationContext appContext: Context,
    ): ExecuteSet {
        return ExecuteSet(speechManager, appContext)
    }
}