package com.count_out.framework.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//In the same package, create a new class called LocalDataSourceModule, in
//which we connect the abstractions to the bindings:

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
//    @Provides
//    fun provideTrainingDao(appDatabase: AppDataBase): TrainingDao = appDatabase.trainingDao()
//    @Provides
//    fun provideRoundDao(appDatabase: AppDataBase): RoundDao = appDatabase.roundDao()
//    @Provides
//    fun provideRingDao(appDatabase: AppDataBase): RingDao = appDatabase.ringDao()
//    @Provides
//    fun provideExerciseDao(appDatabase: AppDataBase): ExerciseDao = appDatabase.exerciseDao()
//    @Provides
//    fun provideActivityDao(appDatabase: AppDataBase): ActivityDao = appDatabase.activityDao()
//    @Provides
//    fun provideSetDao(appDatabase: AppDataBase): SetDao = appDatabase.setDao()
//    @Provides
//    fun provideSpeechDao(appDatabase: AppDataBase): SpeechDao = appDatabase.speechDao()
//    @Provides
//    fun provideSpeechKitDao(appDatabase: AppDataBase): SpeechKitDao = appDatabase.speechKitDao()
//    @Provides
//    fun provideSettingDao(appDatabase: AppDataBase): SettingDao = appDatabase.settingDao()
//    @Provides
//    fun provideTrackingDao(appDatabase: AppDataBase): TrackingDao = appDatabase.trackingDao()
//    @Provides
//    fun provideSpeechSource(speechDao:SpeechDao): SpeechSource = SpeechSourceImpl(speechDao)
//    @Provides
//    fun provideSpeechKitSource(dao:SpeechKitDao, speechSource: SpeechSource): SpeechKitSource =
//        SpeechKitSourceImpl(speechSource, dao)
//    @Provides
//    fun provideSetSource(dao: SetDao, speechKit: SpeechKitSource): SetSource = SetSourceImpl(speechKit, dao)
//    @Provides
//    fun provideActivitySource(dao:ActivityDao, speechKit: SpeechKitSource): ActivitySource =
//        ActivitySourceImpl(speechKit, dao)
//    @Provides
//    fun provideExerciseSource(dao: ExerciseDao, setSource: SetSource, speechKit: SpeechKitSource): ExerciseSource =
//        ExerciseSourceImpl(dao, setSource, speechKit)
//    @Provides
//    fun provideRoundSource(dao: RoundDao, exerciseSource: ExerciseSource, speechKit: SpeechKitSource): RoundSource =
//        RoundSourceImpl(dao, exerciseSource, speechKit)
//    @Provides
//    fun provideRingSource(dao: RingDao, exerciseSource: ExerciseSource, speechKit: SpeechKitSource): RingSource =
//        RingSourceImpl(dao, exerciseSource, speechKit)
//    @Provides
//    fun provideTrainingSource(dao: TrainingDao, roundSource: RoundSource, ringSource: RingSource, speechKit: SpeechKitSource): TrainingSource =
//        TrainingSourceImpl(dao, roundSource, ringSource, speechKit)
}
