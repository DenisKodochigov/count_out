package com.example.framework.injection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.framework.room.AppDataBase
import com.example.framework.room.db.activity.ActivityDao
import com.example.framework.room.db.set.SetDao
import com.example.framework.room.db.training.TrainingDao
import com.example.framework.room.entity.prepopulateRealDb
import com.example.framework.room.entity.prepopulateTestDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    lateinit var database: AppDataBase
    private var mode: Int = 1
//    @Provides
//    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase =
//        Room.databaseBuilder(context, AppDataBase::class.java, "count_out").build()

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

    @Provides
    fun provideActivityDao(appDatabase: AppDataBase): ActivityDao = appDatabase.activityDao()
    @Provides
    fun provideTrainingDao(appDatabase: AppDataBase): TrainingDao = appDatabase.trainingDao()
    @Provides
    fun provideSetDao(appDatabase: AppDataBase): SetDao = appDatabase.setDao()
}