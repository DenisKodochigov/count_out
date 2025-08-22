package com.count_out.domain.di

//import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.domain.core.plans.ActivityCore
import com.count_out.domain.repository.BluetoothRepo
import com.count_out.domain.repository.CountOutServiceRepo
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.repository.WeatherRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.repository.plans.SetRepo
import com.count_out.domain.repository.plans.SettingsRepo
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.CountOutServiceBindUC
import com.count_out.domain.use_case.other.CountOutServiceUnBindUC
import com.count_out.domain.use_case.other.GetWeatherUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.CopyTrainingUC
import com.count_out.domain.use_case.plans.DeleteTrainingUC
import com.count_out.domain.use_case.plans.GetStepPlanUC
import com.count_out.domain.use_case.plans.GetTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.SaveLastUsePlanUC
import com.count_out.domain.use_case.plans.SelectTrainingUC
import com.count_out.domain.use_case.plans.UpdateTrainingUC
import com.count_out.domain.use_case.plans.activity.AddActivityUC
import com.count_out.domain.use_case.plans.activity.DeleteActivityUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.activity.UpdateActivityUC
import com.count_out.domain.use_case.plans.exercise.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.plans.exercise.CopyExerciseUC
import com.count_out.domain.use_case.plans.exercise.DeleteExerciseUC
import com.count_out.domain.use_case.plans.exercise.UpdateExerciseUC
import com.count_out.domain.use_case.plans.set.CopySetUC
import com.count_out.domain.use_case.plans.set.DeleteSetUC
import com.count_out.domain.use_case.plans.set.UpdateSetUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.domain.use_case.workout.DownIntervalUC
import com.count_out.domain.use_case.workout.PauseWorkoutUC
import com.count_out.domain.use_case.workout.SaveWorkoutUC
import com.count_out.domain.use_case.workout.StartWorkoutUC
import com.count_out.domain.use_case.workout.StopWorkoutUC
import com.count_out.domain.use_case.workout.UpIntervalUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
//    @Singleton
//    @Provides
//    fun provideUseCaseConfiguration(): UseCase.Configuration = UseCase.Configuration(Dispatchers.IO)
//
//    @Singleton
//    @Provides
//    fun provideCopyTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): CopyTrainingUC = CopyTrainingUC(configuration, trainingRepo)
//    @Singleton
//    @Provides
//    fun provideDelTrainingUCUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): DeleteTrainingUC = DeleteTrainingUC(configuration, trainingRepo)
//    @Singleton
//    @Provides
//    fun provideGetTrainingsUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): GetTrainingsUC = GetTrainingsUC(configuration, trainingRepo)
//    @Singleton
//    @Provides
//    fun provideGetTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): GetTrainingUC = GetTrainingUC(configuration, trainingRepo)
//    @Singleton
//    @Provides
//    fun provideSelectTrainingUseCase(
//        configuration: UseCase.Configuration,
//        lastPlanRepo: LastPlanRepo): SelectTrainingUC = SelectTrainingUC(configuration, lastPlanRepo)
//    @Singleton
//    @Provides
//    fun provideUpdateTrainingUseCase(
//        configuration: UseCase.Configuration,
//        trainingRepo: TrainingRepo): UpdateTrainingUC = UpdateTrainingUC(configuration, trainingRepo)
//
////
////    @Provides
////    fun provideAddActivity1UseCase(
////        configuration: UseCase.Configuration, core: ActivityCore
////    ): AddActivity1UC = AddActivity1UC(configuration, core)
////
//
//
//    @Singleton
//    @Provides
//    fun provideAddActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): AddActivityUC = AddActivityUC(configuration, activityRepo)
//    @Singleton
//    @Provides
//    fun provideDelActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): DeleteActivityUC = DeleteActivityUC(configuration, activityRepo)
//    @Singleton
//    @Provides
//    fun provideGetsActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): GetActivitiesUC = GetActivitiesUC(configuration, activityRepo)
//    @Singleton
//    @Provides
//    fun provideUpdateActivityUseCase(
//        configuration: UseCase.Configuration,
//        activityRepo: ActivityRepo ): UpdateActivityUC = UpdateActivityUC(configuration, activityRepo)
//    @Singleton
//    @Provides
//    fun provideClearCacheBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): ClearCacheBleUC = ClearCacheBleUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideSelectDeviceBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): SelectDeviceBleUC = SelectDeviceBleUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideStartScanBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): StartScanBleUC = StartScanBleUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideLastBleDeviceUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): LastBleDeviceUC = LastBleDeviceUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideStopScanBleUseCase(
//        configuration: UseCase.Configuration,
//        repo: BluetoothRepo ): StopScanBleUC = StopScanBleUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideChangeSequenceExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): ChangeSequenceExerciseUC = ChangeSequenceExerciseUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideCopyExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): CopyExerciseUC = CopyExerciseUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideDeleteExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): DeleteExerciseUC = DeleteExerciseUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideUpdateExerciseUseCase(
//        configuration: UseCase.Configuration,
//        repo: ExerciseRepo ): UpdateExerciseUC = UpdateExerciseUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideCopySetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): CopySetUC = CopySetUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideDeleteSetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): DeleteSetUC = DeleteSetUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideUpdateSetUseCase(
//        configuration: UseCase.Configuration,
//        repo: SetRepo ): UpdateSetUC = UpdateSetUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideGetSettingsUseCase(
//        configuration: UseCase.Configuration,
//        repo: SettingsRepo ): GetSettingsUC = GetSettingsUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideUpdateSettingUseCase(
//        configuration: UseCase.Configuration,
//        repo: SettingsRepo ): UpdateSettingUC = UpdateSettingUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideGetWeatherUseCase(
//        configuration: UseCase.Configuration,
//        repo: WeatherRepo ): GetWeatherUC = GetWeatherUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideCountOutServiceBindUseCase(
//        configuration: UseCase.Configuration,
//        repo: CountOutServiceRepo
//    ): CountOutServiceBindUC = CountOutServiceBindUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideCountOutServiceUnBindUseCase(
//        configuration: UseCase.Configuration,
//        repo: CountOutServiceRepo
//    ): CountOutServiceUnBindUC = CountOutServiceUnBindUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideCollapsingUseCase(configuration: UseCase.Configuration, ): CollapsingUC =
//        CollapsingUC(configuration)
//    @Singleton
//    @Provides
//    fun provideShowBottomSheetUseCase(configuration: UseCase.Configuration): ShowBottomSheetUC =
//        ShowBottomSheetUC(configuration)
//    @Singleton
//    @Provides
//    fun provideDownIntervalUseCase(configuration: UseCase.Configuration, repo: ExecuteWorkOutRepo): DownIntervalUC =
//        DownIntervalUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideGetStepPlanUseCase(configuration: UseCase.Configuration,
//            repo: ExecuteWorkOutRepo, repo1: LastPlanRepo): GetStepPlanUC =
//        GetStepPlanUC(configuration, repo, repo1)
//    @Singleton
//    @Provides
//    fun provideUpIntervalUseCase(configuration: UseCase.Configuration, repo: ExecuteWorkOutRepo): UpIntervalUC =
//        UpIntervalUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun providePauseWorkoutUseCase(configuration: UseCase.Configuration, repo: ExecuteWorkOutRepo): PauseWorkoutUC =
//        PauseWorkoutUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideSaveWorkoutUseCase(configuration: UseCase.Configuration, repo: ExecuteWorkOutRepo): SaveWorkoutUC =
//        SaveWorkoutUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideStartWorkoutUseCase(configuration: UseCase.Configuration, repo: ExecuteWorkOutRepo): StartWorkoutUC =
//        StartWorkoutUC(configuration, repo)
//    @Singleton
//    @Provides
//    fun provideStopWorkoutUseCase(configuration: UseCase.Configuration, repo: ExecuteWorkOutRepo): StopWorkoutUC =
//        StopWorkoutUC(configuration, repo)
////    @Singleton
////    @Provides
////    fun provideGetPlanUseCase(configuration: UseCase.Configuration, repo: LastPlanRepo): GetPlanUC =
////        GetPlanUC(configuration, repo)
//
//    @Singleton
//    @Provides
//    fun provideSavePlanUseCase(configuration: UseCase.Configuration, repo: LastPlanRepo): SaveLastUsePlanUC =
//        SaveLastUsePlanUC(configuration, repo)
}