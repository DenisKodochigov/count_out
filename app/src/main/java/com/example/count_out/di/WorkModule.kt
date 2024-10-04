package com.example.count_out.di

import com.example.count_out.domain.SpeechManager
import com.example.count_out.service_count_out.work.WorkN
import com.example.count_out.service_count_out.work.execute.ExecuteWorkN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkModule {

    @Singleton
    @Provides
    fun provideWorkOutService(speechManager: SpeechManager, executeWork: ExecuteWorkN): WorkN {
        return WorkN(speechManager, executeWork)
    }
}