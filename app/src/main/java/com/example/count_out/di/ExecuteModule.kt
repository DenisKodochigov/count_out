package com.example.count_out.di

import android.content.Context
import com.example.count_out.domain.SpeechManager
import com.example.count_out.service_count_out.work.execute.ExecuteExerciseN
import com.example.count_out.service_count_out.work.execute.ExecuteRoundN
import com.example.count_out.service_count_out.work.execute.ExecuteWorkN
import com.example.count_out.service_count_out.work.execute.ExecuteSetN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExecuteModule {
//    @Singleton
//    @Provides
//    fun provideExecuteWorkOut(speechManager: SpeechManager, executeRound: ExecuteRound): ExecuteWork {
//        return ExecuteWork(speechManager, executeRound)
//    }
//    @Singleton
//    @Provides
//    fun provideExecuteRound(speechManager: SpeechManager, executeExercise: ExecuteExercise): ExecuteRound {
//        return ExecuteRound(speechManager, executeExercise)
//    }
//    @Singleton
//    @Provides
//    fun provideExecuteExercise(speechManager: SpeechManager, executeSet: ExecuteSet): ExecuteExercise {
//        return ExecuteExercise(speechManager, executeSet)
//    }
//    @Singleton
//    @Provides
//    fun provideExecuteSet(speechManager: SpeechManager, @ApplicationContext appContext: Context): ExecuteSet {
//        return ExecuteSet(speechManager, appContext)
//    }

    @Singleton
    @Provides
    fun provideExecuteWorkOut(speechManager: SpeechManager, executeRound: ExecuteRoundN): ExecuteWorkN {
        return ExecuteWorkN(speechManager, executeRound)
    }
    @Singleton
    @Provides
    fun provideExecuteRound(speechManager: SpeechManager, executeExercise: ExecuteExerciseN): ExecuteRoundN {
        return ExecuteRoundN(speechManager, executeExercise)
    }
    @Singleton
    @Provides
    fun provideExecuteExercise(speechManager: SpeechManager, executeSet: ExecuteSetN): ExecuteExerciseN {
        return ExecuteExerciseN(speechManager, executeSet)
    }
    @Singleton
    @Provides
    fun provideExecuteSet(speechManager: SpeechManager, @ApplicationContext appContext: Context): ExecuteSetN {
        return ExecuteSetN(speechManager, appContext)
    }
}