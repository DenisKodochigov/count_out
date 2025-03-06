package com.count_out.domain.di

import com.count_out.domain.use_case.UseCase
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
//
//    @Provides
//    fun provideAddTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): AddTrainingUC = AddTrainingUC(configuration, trainingRepo)
//    @Provides
//    fun provideCopyTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): CopyTrainingUC = CopyTrainingUC(configuration, trainingRepo)
//    @Provides
//    fun provideDelTrainingUCUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): DeleteTrainingUC = DeleteTrainingUC(configuration, trainingRepo)
//    @Provides
//    fun provideGetTrainingsUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): GetTrainingsUC = GetTrainingsUC(configuration, trainingRepo)
//    @Provides
//    fun provideGetTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): GetTrainingUC = GetTrainingUC(configuration, trainingRepo)
//    @Provides
//    fun provideSelectTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): SelectTrainingUC = SelectTrainingUC(configuration, trainingRepo)
//    @Provides
//    fun provideUpdateTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): UpdateTrainingUC = UpdateTrainingUC(configuration, trainingRepo)
//    @Provides
//    fun provideAddActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): AddActivityUC = AddActivityUC(configuration, activityRepo)
//    @Provides
//    fun provideDelActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): DeleteActivityUC = DeleteActivityUC(configuration, activityRepo)
//    @Provides
//    fun provideGetsActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): GetsActivityUC = GetsActivityUC(configuration, activityRepo)
//    @Provides
//    fun provideSetColorActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): SetColorActivityUC = SetColorActivityUC(configuration, activityRepo)
//    @Provides
//    fun provideUpdateActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): UpdateActivityUC = UpdateActivityUC(configuration, activityRepo)
//    @Provides
//    fun provideClearCacheBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): ClearCacheBleUC = ClearCacheBleUC(configuration, repo)
//    @Provides
//    fun provideSelectDeviceBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): SelectDeviceBleUC = SelectDeviceBleUC(configuration, repo)
//    @Provides
//    fun provideStartScanBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): StartScanBleUC = StartScanBleUC(configuration, repo)
//    @Provides
//    fun provideStopScanBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): StopScanBleUC = StopScanBleUC(configuration, repo)
//    @Provides
//    fun provideAddExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): AddExerciseUC = AddExerciseUC(configuration, repo)
//    @Provides
//    fun provideChangeSequenceExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): ChangeSequenceExerciseUC = ChangeSequenceExerciseUC(configuration, repo)
//    @Provides
//    fun provideCopyExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): CopyExerciseUC = CopyExerciseUC(configuration, repo)
//    @Provides
//    fun provideDeleteExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): DeleteExerciseUC = DeleteExerciseUC(configuration, repo)
//    @Provides
//    fun provideSelectActivityUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): SelectActivityUC = SelectActivityUC(configuration, repo)
//    @Provides
//    fun provideAddSetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): AddSetUC = AddSetUC(configuration, repo)
//    @Provides
//    fun provideCopySetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): CopySetUC = CopySetUC(configuration, repo)
//    @Provides
//    fun provideDeleteSetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): DeleteSetUC = DeleteSetUC(configuration, repo)
//    @Provides
//    fun provideUpdateSetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): UpdateSetUC = UpdateSetUC(configuration, repo)
//    @Provides
//    fun provideGetSettingsUseCase(
//        configuration: UseCase.Configuration,
//        repo: SettingsRepo ): GetSettingsUC = GetSettingsUC(configuration, repo)
//    @Provides
//    fun provideGetSettingUseCase(
//        configuration: UseCase.Configuration,
//        repo: SettingsRepo ): GetSettingUC = GetSettingUC(configuration, repo)
//    @Provides
//    fun provideUpdateSettingUseCase(
//        configuration: UseCase.Configuration,
//        repo: SettingsRepo ): UpdateSettingUC = UpdateSettingUC(configuration, repo)
//    @Provides
//    fun provideGetWeatherUseCase(
//        configuration: UseCase.Configuration,
//        repo: WeatherRepo ): GetWeatherUC = GetWeatherUC(configuration, repo)
//    @Provides
//    fun provideCountOutServiceBindUseCase(
//        configuration: UseCase.Configuration,
//        repo: CountOutServiceRepo
//    ): CountOutServiceBindUC = CountOutServiceBindUC(configuration, repo)
//    @Provides
//    fun provideCountOutServiceUnBindUseCase(
//        configuration: UseCase.Configuration,
//        repo: CountOutServiceRepo
//    ): CountOutServiceUnBindUC = CountOutServiceUnBindUC(configuration, repo)
}