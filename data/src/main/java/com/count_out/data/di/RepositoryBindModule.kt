package com.count_out.data.di

import com.count_out.data.models.ActivityImpl
import com.count_out.data.repository.ActivityRepoImpl
import com.count_out.data.repository.BluetoothRepoImpl
import com.count_out.data.repository.CountOutServiceRepoImpl
import com.count_out.data.repository.ExecuteWorkOutRepoImpl
import com.count_out.data.repository.ExerciseRepoImpl
import com.count_out.data.repository.LastPlanRepoImpl
import com.count_out.data.repository.LocationRepoImpl
import com.count_out.data.repository.RingRepoImpl
import com.count_out.data.repository.RoundRepoImpl
import com.count_out.data.repository.SetRepoImpl
import com.count_out.data.repository.SettingsRepoImpl
import com.count_out.data.repository.SpeechKitRepoImpl
import com.count_out.data.repository.SpeechRepoImpl
import com.count_out.data.repository.TrainingRepoImpl
import com.count_out.data.repository.WeatherRepoImpl
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.repository.LocationRepo
import com.count_out.domain.repository.WeatherRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.repository.plans.RingRepo
import com.count_out.domain.repository.plans.RoundRepo
import com.count_out.domain.repository.plans.SetRepo
import com.count_out.domain.repository.plans.SettingsRepo
import com.count_out.domain.repository.plans.SpeechKitRepo
import com.count_out.domain.repository.plans.SpeechRepo
import com.count_out.domain.repository.plans.TrainingRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
    @Binds
    abstract fun bindCountOutServiceRepo(repoImpl: CountOutServiceRepoImpl): CountOutServiceRepo
    @Binds
    abstract fun bindActivityRepo(activityRepoImpl: ActivityRepoImpl): ActivityRepo
    @Binds
    abstract fun bindBluetoothRepo(bluetoothRepoImpl: BluetoothRepoImpl): BluetoothRepo
    @Binds
    abstract fun bindSettingsRepo(settingsRepo: SettingsRepoImpl): SettingsRepo
    @Binds
    abstract fun bindTrainingRepo(trainingRepoImpl: TrainingRepoImpl): TrainingRepo
    @Binds
    abstract fun bindExerciseRepo(exerciseRepoImpl: ExerciseRepoImpl): ExerciseRepo
    @Binds
    abstract fun bindSetRepo(setRepoImpl: SetRepoImpl): SetRepo
    @Binds
    abstract fun bindRingRepo( ringRepoImpl: RingRepoImpl): RingRepo
    @Binds
    abstract fun bindRoundRepo( roundRepoImpl: RoundRepoImpl): RoundRepo
    @Binds
    abstract fun bindSpeechKitRepo(speechKitRepoImpl: SpeechKitRepoImpl): SpeechKitRepo
    @Binds
    abstract fun bindSpeechRepo(speechRepoImpl: SpeechRepoImpl): SpeechRepo
    @Binds
    abstract fun bindLocationRepo(locationRepoImpl: LocationRepoImpl): LocationRepo
    @Binds
    abstract fun bindWeatherRepo(weatherRepoImpl: WeatherRepoImpl): WeatherRepo
    @Binds
    abstract fun bindExecuteWorkOutRepo(executeRepoImpl: ExecuteWorkOutRepoImpl): ExecuteWorkOutRepo
    @Binds
    abstract fun bindlastPlanRepo(lastPlanRepoImpl: LastPlanRepoImpl): LastPlanRepo
    @Binds
    abstract fun bindActive(activityImpl: ActivityImpl): Activity
}