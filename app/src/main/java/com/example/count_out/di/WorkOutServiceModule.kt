package com.example.count_out.di

import android.content.Context
import com.example.count_out.service.ServiceUtils
import com.example.count_out.service.workout.ServiceManager
import com.example.count_out.service.workout.WorkoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkOutServiceModule {

    @Singleton
    @Provides
    fun provideWorkOutService(): WorkoutService {
        return WorkoutService()
    }
    @Singleton
    @Provides
    fun provideServiceManager(@ApplicationContext context: Context, serviceUtils: ServiceUtils): ServiceManager {
        return ServiceManager(context, serviceUtils)
    }
}