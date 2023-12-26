package com.example.count_out.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.CountDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB

@Database(entities = [
    TrainingDB::class,
    RoundDB::class,
    ExerciseDB::class,
    ActivityDB::class,
    SetDB::class,
    SpeechDB::class,
    CountDB::class,
    ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
