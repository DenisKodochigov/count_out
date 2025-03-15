package com.count_out.app.di

import android.content.Context
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.repository.WeatherRepo
import com.count_out.domain.repository.trainings.ActivityRepo
import com.count_out.domain.repository.trainings.ExerciseRepo
import com.count_out.domain.repository.trainings.SetRepo
import com.count_out.domain.repository.trainings.SettingsRepo
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.activity.AddActivityUC
import com.count_out.domain.use_case.activity.DeleteActivityUC
import com.count_out.domain.use_case.activity.GetsActivityUC
import com.count_out.domain.use_case.activity.UpdateActivityUC
import com.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.exercise.CopyExerciseUC
import com.count_out.domain.use_case.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.CountOutServiceBindUC
import com.count_out.domain.use_case.other.CountOutServiceUnBindUC
import com.count_out.domain.use_case.other.GetWeatherUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.set.CopySetUC
import com.count_out.domain.use_case.set.DeleteSetUC
import com.count_out.domain.use_case.set.UpdateSetUC
import com.count_out.domain.use_case.settings.GetSettingUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.models.Internet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideInternet(@ApplicationContext appContext: Context): Internet {
        return Internet(appContext)
    }
    @Singleton
    @Provides
    fun provideUseCaseConfiguration(): UseCase.Configuration = UseCase.Configuration(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideCopyTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): CopyTrainingUC = CopyTrainingUC(configuration, trainingRepo)
    @Singleton
    @Provides
    fun provideDelTrainingUCUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): DeleteTrainingUC = DeleteTrainingUC(configuration, trainingRepo)
    @Singleton
    @Provides
    fun provideGetTrainingsUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): GetTrainingsUC = GetTrainingsUC(configuration, trainingRepo)
    @Singleton
    @Provides
    fun provideGetTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): GetTrainingUC = GetTrainingUC(configuration, trainingRepo)
    @Singleton
    @Provides
    fun provideSelectTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): SelectTrainingUC = SelectTrainingUC(configuration, trainingRepo)
    @Singleton
    @Provides
    fun provideUpdateTrainingUseCase(
        configuration: UseCase.Configuration,
        trainingRepo: TrainingRepo): UpdateTrainingUC = UpdateTrainingUC(configuration, trainingRepo)
    @Singleton
    @Provides
    fun provideAddActivityUseCase(
        configuration: UseCase.Configuration,
        activityRepo: ActivityRepo ): AddActivityUC = AddActivityUC(configuration, activityRepo)
    @Singleton
    @Provides
    fun provideDelActivityUseCase(
        configuration: UseCase.Configuration,
        activityRepo: ActivityRepo ): DeleteActivityUC = DeleteActivityUC(configuration, activityRepo)
    @Singleton
    @Provides
    fun provideGetsActivityUseCase(
        configuration: UseCase.Configuration,
        activityRepo: ActivityRepo ): GetsActivityUC = GetsActivityUC(configuration, activityRepo)
    @Singleton
    @Provides
    fun provideUpdateActivityUseCase(
        configuration: UseCase.Configuration,
        activityRepo: ActivityRepo ): UpdateActivityUC = UpdateActivityUC(configuration, activityRepo)
    @Singleton
    @Provides
    fun provideClearCacheBleUseCase(
        configuration: UseCase.Configuration,
        repo: BluetoothRepo ): ClearCacheBleUC = ClearCacheBleUC(configuration, repo)
    @Singleton
    @Provides
    fun provideSelectDeviceBleUseCase(
        configuration: UseCase.Configuration,
        repo: BluetoothRepo ): SelectDeviceBleUC = SelectDeviceBleUC(configuration, repo)
    @Singleton
    @Provides
    fun provideStartScanBleUseCase(
        configuration: UseCase.Configuration,
        repo: BluetoothRepo ): StartScanBleUC = StartScanBleUC(configuration, repo)
    @Singleton
    @Provides
    fun provideStopScanBleUseCase(
        configuration: UseCase.Configuration,
        repo: BluetoothRepo ): StopScanBleUC = StopScanBleUC(configuration, repo)
    @Singleton
    @Provides
    fun provideChangeSequenceExerciseUseCase(
        configuration: UseCase.Configuration,
        repo: ExerciseRepo ): ChangeSequenceExerciseUC = ChangeSequenceExerciseUC(configuration, repo)
    @Singleton
    @Provides
    fun provideCopyExerciseUseCase(
        configuration: UseCase.Configuration,
        repo: ExerciseRepo ): CopyExerciseUC = CopyExerciseUC(configuration, repo)
    @Singleton
    @Provides
    fun provideDeleteExerciseUseCase(
        configuration: UseCase.Configuration,
        repo: ExerciseRepo ): DeleteExerciseUC = DeleteExerciseUC(configuration, repo)
    @Singleton
    @Provides
    fun provideUpdateExerciseUseCase(
        configuration: UseCase.Configuration,
        repo: ExerciseRepo ): UpdateExerciseUC = UpdateExerciseUC(configuration, repo)
//    @Provides
//    fun provideSelectActivityUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): SelectActivityUC = SelectActivityUC(configuration, repo)
    @Singleton
    @Provides
    fun provideCopySetUseCase(
        configuration: UseCase.Configuration,
        repo: SetRepo ): CopySetUC = CopySetUC(configuration, repo)
    @Singleton
    @Provides
    fun provideDeleteSetUseCase(
        configuration: UseCase.Configuration,
        repo: SetRepo ): DeleteSetUC = DeleteSetUC(configuration, repo)
    @Singleton
    @Provides
    fun provideUpdateSetUseCase(
        configuration: UseCase.Configuration,
        repo: SetRepo ): UpdateSetUC = UpdateSetUC(configuration, repo)
    @Singleton
    @Provides
    fun provideGetSettingsUseCase(
        configuration: UseCase.Configuration,
        repo: SettingsRepo ): GetSettingsUC = GetSettingsUC(configuration, repo)
    @Singleton
    @Provides
    fun provideGetSettingUseCase(
        configuration: UseCase.Configuration,
        repo: SettingsRepo ): GetSettingUC = GetSettingUC(configuration, repo)
    @Singleton
    @Provides
    fun provideUpdateSettingUseCase(
        configuration: UseCase.Configuration,
        repo: SettingsRepo ): UpdateSettingUC = UpdateSettingUC(configuration, repo)
    @Singleton
    @Provides
    fun provideGetWeatherUseCase(
        configuration: UseCase.Configuration,
        repo: WeatherRepo ): GetWeatherUC = GetWeatherUC(configuration, repo)
    @Singleton
    @Provides
    fun provideCountOutServiceBindUseCase(
        configuration: UseCase.Configuration,
        repo: CountOutServiceRepo
    ): CountOutServiceBindUC = CountOutServiceBindUC(configuration, repo)
    @Singleton
    @Provides
    fun provideCountOutServiceUnBindUseCase(
        configuration: UseCase.Configuration,
        repo: CountOutServiceRepo
    ): CountOutServiceUnBindUC = CountOutServiceUnBindUC(configuration, repo)
    @Singleton
    @Provides
    fun provideCollapsingUseCase(configuration: UseCase.Configuration, ): CollapsingUC =
        CollapsingUC(configuration)
    @Singleton
    @Provides
    fun provideShowBottomSheetUseCase(configuration: UseCase.Configuration): ShowBottomSheetUC =
        ShowBottomSheetUC(configuration)

}