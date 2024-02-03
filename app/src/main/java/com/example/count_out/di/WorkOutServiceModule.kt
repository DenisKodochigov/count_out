package com.example.count_out.di

import android.content.Context
import com.example.count_out.service.WorkoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkOutServiceModule {
    @Singleton
    @Provides
    fun provideWorkOutService(@ApplicationContext appContext: Context): WorkoutService {
        return WorkoutService(  )
    }
}