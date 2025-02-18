package com.example.domain.di

import com.example.domain.repository.WeatherRepo
import com.example.domain.repository.trainings.ActivityRepo
import com.example.domain.repository.trainings.TrainingRepo
import com.example.domain.use_case.UseCase
import com.example.domain.use_case.trainings.AddTrainingUC
import com.example.domain.use_case.trainings.CopyTrainingUC
import com.example.domain.use_case.trainings.DelTrainingUC
import com.example.domain.use_case.trainings.GetActivitiesUC
import com.example.domain.use_case.trainings.GetTrainingUC
import com.example.domain.use_case.trainings.GetTrainingsUC
import com.example.domain.use_case.trainings.GetWeatherUC
import com.example.domain.use_case.trainings.SelectTrainingUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCaseConfiguration(): UseCase.Configuration = UseCase.Configuration(Dispatchers.IO)

    @Provides
    fun provideAddTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): AddTrainingUC = AddTrainingUC(configuration, trainingRepo)
    @Provides
    fun provideCopyTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): CopyTrainingUC = CopyTrainingUC(configuration, trainingRepo)
    @Provides
    fun provideDelTrainingUCUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): DelTrainingUC = DelTrainingUC(configuration, trainingRepo)
    @Provides
    fun provideGetTrainingsUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): GetTrainingsUC = GetTrainingsUC(configuration, trainingRepo)
    @Provides
    fun provideGetTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): GetTrainingUC = GetTrainingUC(configuration, trainingRepo)
    @Provides
    fun provideSelectTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): SelectTrainingUC = SelectTrainingUC(configuration, trainingRepo)
    @Provides
    fun provideGetWeatherUseCase(
        configuration: UseCase.Configuration,
        weatherRepo: WeatherRepo ): GetWeatherUC = GetWeatherUC(configuration, weatherRepo)
    @Provides
    fun provideGetActivitiesUseCase(
        configuration: UseCase.Configuration,
        activityRepo: ActivityRepo ): GetActivitiesUC = GetActivitiesUC(configuration, activityRepo)
}