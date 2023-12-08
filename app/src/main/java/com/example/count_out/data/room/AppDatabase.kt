package com.example.count_out.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.count_out.data.room.tables.WorkoutDB

@Database(entities = [
    WorkoutDB::class,
//    RoundDB::class,
//    SetDB::class,
//    CountDB::class,
    ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
