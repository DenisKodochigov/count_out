package com.count_out.framework.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.count_out.framework.room.AppDataBase
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.round.RoundDao
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.settings.SettingDao
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.db.traking.TrackingDao
import com.count_out.framework.room.entity.prepopulateRealDb
import com.count_out.framework.room.entity.prepopulateTestDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    lateinit var database: AppDataBase
    private var mode: Int = 1

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase {
        when (mode) {
            0 -> database = Room.inMemoryDatabaseBuilder( appContext, AppDataBase::class.java).build()
            1 -> { database = Room.inMemoryDatabaseBuilder(appContext, AppDataBase::class.java)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Thread { prepopulateTestDb(database) }.start()
                    }
                })
                .build()
            }
            2 -> { database = Room.databaseBuilder(appContext, AppDataBase::class.java, "count_out.db")
                .addCallback( object: RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Thread { prepopulateRealDb(database) }.start()
                    }
                })
                .build()
            }
            3 -> {
                database = Room.inMemoryDatabaseBuilder(appContext, AppDataBase::class.java)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateRealDb(database) }.start()
                        }
                    })
                    .build()
            }
            else -> {
                database = Room.databaseBuilder( appContext, AppDataBase::class.java, "count_out.db").build()
            }
        }
        return database
    }
    @Singleton
    @Provides
    fun provideTrainingDao(appDatabase: AppDataBase): TrainingDao = appDatabase.trainingDao()
    @Singleton
    @Provides
    fun provideRoundDao(appDatabase: AppDataBase): RoundDao = appDatabase.roundDao()
    @Singleton
    @Provides
    fun provideRingDao(appDatabase: AppDataBase): RingDao = appDatabase.ringDao()
    @Singleton
    @Provides
    fun provideExerciseDao(appDatabase: AppDataBase): ExerciseDao = appDatabase.exerciseDao()
    @Singleton
    @Provides
    fun provideActivityDao(appDatabase: AppDataBase): ActivityDao = appDatabase.activityDao()
    @Singleton
    @Provides
    fun provideSetDao(appDatabase: AppDataBase): SetDao = appDatabase.setDao()
    @Singleton
    @Provides
    fun provideSpeechDao(appDatabase: AppDataBase): SpeechDao = appDatabase.speechDao()
    @Singleton
    @Provides
    fun provideSpeechKitDao(appDatabase: AppDataBase): SpeechKitDao = appDatabase.speechKitDao()
    @Singleton
    @Provides
    fun provideSettingDao(appDatabase: AppDataBase): SettingDao = appDatabase.settingDao()
    @Singleton
    @Provides
    fun provideTrackingDao(appDatabase: AppDataBase): TrackingDao = appDatabase.trackingDao()

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