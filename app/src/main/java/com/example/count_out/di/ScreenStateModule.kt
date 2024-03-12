package com.example.count_out.di

import com.example.count_out.ui.screens.play_workout.PlayWorkoutScreenState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ScreenStateModule {

    @Singleton
    @Provides
    fun providePlayWorkoutScreenState(): PlayWorkoutScreenState {
        return PlayWorkoutScreenState( )
    }
}