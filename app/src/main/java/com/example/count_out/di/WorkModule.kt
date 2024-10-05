package com.example.count_out.di

import com.example.count_out.domain.SpeechManager
import com.example.count_out.service_count_out.work.Work
import com.example.count_out.service_count_out.work.execute.ExecuteWork
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
    fun provideWorkOutService(speechManager: SpeechManager, executeWork: ExecuteWork): Work {
        return Work(speechManager, executeWork)
    }
}