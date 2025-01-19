package com.example.data.injection

import com.example.data.repository.ActivityRepoImpl
import com.example.data.repository.TrainingRepoImpl
import com.example.data.repository.WeatherRepoImpl
import com.example.domain.repository.training.ActivityRepo
import com.example.domain.repository.training.TrainingRepo
import com.example.domain.repository.WeatherRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTrainingRepo(trainingRepoImpl: TrainingRepoImpl): TrainingRepo

    @Binds
    abstract fun bindActivityRepo(activityRepoImpl: ActivityRepoImpl): ActivityRepo

    @Binds
    abstract fun bindWeatherRepo(weatherRepoImpl: WeatherRepoImpl): WeatherRepo
}