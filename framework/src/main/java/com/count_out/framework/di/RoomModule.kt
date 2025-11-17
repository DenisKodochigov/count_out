package com.count_out.framework.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.count_out.framework.room.AppDataBase
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.part.PartDao
import com.count_out.framework.room.db.plan.PlanDao
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.settings.SettingDao
import com.count_out.framework.room.db.speech.SpeechDao
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
    @Singleton @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase {
        val builder = when (mode) {
            0 -> Room.inMemoryDatabaseBuilder(appContext, AppDataBase::class.java)
            1 -> Room.inMemoryDatabaseBuilder(appContext, AppDataBase::class.java)
                .addPrepopulate { prepopulateTestDb(database) }
            2 -> Room.databaseBuilder(appContext, AppDataBase::class.java, "count_out.db")
                .addPrepopulate { prepopulateRealDb(database) }
            3 -> Room.inMemoryDatabaseBuilder(appContext, AppDataBase::class.java)
                .addPrepopulate { prepopulateRealDb(database) }
            else -> Room.databaseBuilder(appContext, AppDataBase::class.java, "count_out.db")
        }
        return builder.build().also { database = it }
    }
    private fun RoomDatabase.Builder<AppDataBase>.addPrepopulate(block: () -> Unit) =
        addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) { Thread(block).start() }
        })
    @Singleton @Provides
    fun providePlanDao(appDatabase: AppDataBase): PlanDao = appDatabase.planDao()
    @Singleton @Provides
    fun providePartDao(appDatabase: AppDataBase): PartDao = appDatabase.partDao()
    @Singleton @Provides
    fun provideRoundDao(appDatabase: AppDataBase): RingDao = appDatabase.ringDao()
    @Singleton @Provides
    fun provideExerciseDao(appDatabase: AppDataBase): ExerciseDao = appDatabase.exerciseDao()
    @Singleton @Provides
    fun provideActivityDao(appDatabase: AppDataBase): ActivityDao = appDatabase.activityDao()
    @Singleton @Provides
    fun provideSetDao(appDatabase: AppDataBase): SetDao = appDatabase.setDao()
    @Singleton @Provides
    fun provideSpeechDao(appDatabase: AppDataBase): SpeechDao = appDatabase.speechDao()
    @Singleton @Provides
    fun provideSettingDao(appDatabase: AppDataBase): SettingDao = appDatabase.settingDao()
    @Singleton @Provides
    fun provideTrackingDao(appDatabase: AppDataBase): TrackingDao = appDatabase.trackingDao()
}
