package com.example.count_out.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.count_out.R
import com.example.count_out.data.room.AppDatabase
import com.example.count_out.data.room.DataDao
import com.example.count_out.data.room.tables.ActivityDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    lateinit var database: AppDatabase
    private const val mode: Int = 1
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        when (mode) {
            0 -> database = Room.inMemoryDatabaseBuilder( appContext, AppDatabase::class.java).build()
            1 -> {
                database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateDb(database) }.start()
                        }
                    })
                    .build()
            }
            2 -> {
                database = Room.databaseBuilder(appContext, AppDatabase::class.java, "data.db")
                    .addCallback( object: RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateDb(database) }.start()
//                            Executors.newSingleThreadExecutor().execute {
//                                database.dataDao().newBasket(BasketEntity(nameBasket = "Test")) }
                        }
                    })
                    .build()
            }
            else -> {
                database = Room
                    .databaseBuilder( appContext, AppDatabase::class.java, "data.db").build()
            }
        }
        return database
    }

    private fun prepopulateDb( db: AppDatabase) {
        db.dataDao().addActivity(ActivityDB(name = "Run", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(name = "Ski", icon = R.drawable.ic_setka))
    }

    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}

