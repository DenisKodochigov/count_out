package com.example.count_out.di

import com.example.count_out.service.ServiceManager
import com.example.count_out.service.WorkoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkOutServiceModule {
    @Singleton
    @Provides
    fun provideWorkOutService(): WorkoutService {
        return WorkoutService(  )
    }
    @Singleton
    @Provides
    fun provideServiceManager(): ServiceManager {
        return ServiceManager()
    }
}