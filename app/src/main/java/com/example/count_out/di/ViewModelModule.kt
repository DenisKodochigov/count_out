package com.example.count_out.di

//@Module
//@InstallIn(SingletonComponent::class)
//class ViewModelModule {
//    @Singleton
//    @Provides
//    fun providePlayWorkoutViewModel(
//        errorApp: ErrorApp,
//        dataRepository: DataRepository,
//        serviceManager: ServiceManager
//    ): PlayWorkoutViewModel {
//        return PlayWorkoutViewModel( errorApp, dataRepository, serviceManager )
//    }
//    @Singleton
//    @Provides
//    fun provideTrainingsViewModel(
//        errorApp: ErrorApp,
//        dataRepository: DataRepository
//    ): TrainingsViewModel {
//        return TrainingsViewModel( errorApp, dataRepository )
//    }
//    @Singleton
//    @Provides
//    fun provideTrainingViewModel(
//        errorApp: ErrorApp,
//        dataRepository: DataRepository
//    ): TrainingViewModel {
//        return TrainingViewModel( errorApp, dataRepository )
//    }
//    @Singleton
//    @Provides
//    fun provideSettingViewModel(
//        errorApp: ErrorApp,
//        dataRepository: DataRepository
//    ): SettingViewModel {
//        return SettingViewModel( errorApp, dataRepository )
//    }
//}