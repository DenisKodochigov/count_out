package com.count_out.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.count_out.app.data.room.AppDatabase
import com.count_out.app.data.room.DataDao
import com.count_out.app.entity.Const.MODE_DATABASE
import com.count_out.app.entity.prepopulateRealDb
import com.count_out.app.entity.prepopulateTestDb
import com.count_out.app.ui.view_components.lg
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    lateinit var database: AppDatabase
    private var mode: Int = MODE_DATABASE
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        when (mode) {
            0 -> database = Room.inMemoryDatabaseBuilder( appContext, AppDatabase::class.java).build()
            1 -> { database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateTestDb(database) }.start()
                        }
                    })
                    .build()
            }
            2 -> { database = Room.databaseBuilder(appContext, AppDatabase::class.java, "data.db")
                    .addCallback( object: RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateRealDb(database) }.start()
                        }
                    })
                    .build()
            }
            3 -> {
                database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateRealDb(database) }.start()
                        }
                    })
                    .build()
            }
            else -> {
                database = Room.databaseBuilder( appContext, AppDatabase::class.java, "data.db").build()
            }
        }
        return database
    }
    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}


