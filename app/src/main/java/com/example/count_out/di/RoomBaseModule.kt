package com.example.count_out.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.count_out.framework.room.db.traking.TrackingDao
import com.example.count_out.entity.Const.MODE_DATABASE
import com.example.count_out.entity.prepopulateRealDb
import com.example.count_out.entity.prepopulateTestDb
import com.example.count_out.framework.room.AppDataBase
import com.example.count_out.framework.room.db.activity.ActivityDao
import com.example.count_out.framework.room.db.exercise.ExerciseDao
import com.example.count_out.framework.room.db.ring.RingDao
import com.example.count_out.framework.room.db.round.RoundDao
import com.example.count_out.framework.room.db.set.SetDao
import com.example.count_out.framework.room.db.settings.SettingDao
import com.example.count_out.framework.room.db.speech.SpeechDao
import com.example.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.example.count_out.framework.room.db.training.TrainingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomBaseModule {
    lateinit var database: AppDataBase
    private var mode: Int = MODE_DATABASE
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase {
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
            2 -> { database = Room.databaseBuilder(appContext, AppDataBase::class.java, "data.db")
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
                database = Room.databaseBuilder( appContext, AppDataBase::class.java, "data.db").build()
            }
        }
        return database
    }

    @Provides
    fun provideTrainingDao(appDatabase: AppDataBase): TrainingDao = appDatabase.trainingDao()
    @Provides
    fun provideRoundDao(appDatabase: AppDataBase): RoundDao = appDatabase.roundDao()
    @Provides
    fun provideRingDao(appDatabase: AppDataBase): RingDao = appDatabase.ringDao()
    @Provides
    fun provideExerciseDao(appDatabase: AppDataBase): ExerciseDao = appDatabase.exerciseDao()
    @Provides
    fun provideActivityDao(appDatabase: AppDataBase): ActivityDao = appDatabase.activityDao()
    @Provides
    fun provideSetDao(appDatabase: AppDataBase): SetDao = appDatabase.setDao()
    @Provides
    fun provideSpeechDao(appDatabase: AppDataBase): SpeechDao = appDatabase.speechDao()
    @Provides
    fun provideSpeechKitDao(appDatabase: AppDataBase): SpeechKitDao = appDatabase.speechKitDao()
    @Provides
    fun provideSettingDao(appDatabase: AppDataBase): SettingDao = appDatabase.settingDao()
    @Provides
    fun provideTrackingDao(appDatabase: AppDataBase): TrackingDao = appDatabase.trackingDao()
}


