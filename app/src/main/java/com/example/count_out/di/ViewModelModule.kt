package com.example.count_out.di

import com.example.count_out.data.DataRepository
import com.example.count_out.entity.ErrorApp
import com.example.count_out.service.ServiceManager
import com.example.count_out.ui.screens.play_workout.PlayWorkoutViewModel
import com.example.count_out.ui.screens.settings.SettingViewModel
import com.example.count_out.ui.screens.training.TrainingViewModel
import com.example.count_out.ui.screens.trainings.TrainingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun providePlayWorkoutViewModel(
        errorApp: ErrorApp,
        dataRepository: DataRepository,
        serviceManager: ServiceManager): PlayWorkoutViewModel {
        return PlayWorkoutViewModel( errorApp, dataRepository, serviceManager )
    }
    @Singleton
    @Provides
    fun provideTrainingsViewModel(
        errorApp: ErrorApp,
        dataRepository: DataRepository): TrainingsViewModel {
        return TrainingsViewModel( errorApp, dataRepository )
    }
    @Singleton
    @Provides
    fun provideTrainingViewModel(
        errorApp: ErrorApp,
        dataRepository: DataRepository): TrainingViewModel {
        return TrainingViewModel( errorApp, dataRepository )
    }
    @Singleton
    @Provides
    fun provideSettingViewModel(
        errorApp: ErrorApp,
        dataRepository: DataRepository): SettingViewModel {
        return SettingViewModel( errorApp, dataRepository )
    }
}