package com.count_out.data.injection

import com.count_out.data.repository.ActivityRepoImpl
import com.count_out.data.repository.TrainingRepoImpl
import com.count_out.data.repository.WeatherRepoImpl
import com.count_out.domain.repository.trainings.ActivityRepo
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.repository.WeatherRepo
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