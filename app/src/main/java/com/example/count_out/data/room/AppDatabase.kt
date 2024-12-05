package com.example.count_out.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.count_out.data.room.relation.ConverterDistanceE
import com.example.count_out.data.room.relation.ConverterGoalSet
import com.example.count_out.data.room.relation.ConverterTimeE
import com.example.count_out.data.room.relation.ConverterWeightE
import com.example.count_out.data.room.relation.ConverterZone
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.CountDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TemporaryDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.data.room.tables.WorkoutDB

@Database(entities = [
    TrainingDB::class,
    RoundDB::class,
    ExerciseDB::class,
    ActivityDB::class,
    SetDB::class,
    SpeechDB::class,
    SpeechKitDB::class,
    CountDB::class,
    SettingDB::class,
    WorkoutDB::class,
    TemporaryDB::class,
    ], version = 1, exportSchema = false)
@TypeConverters(
    ConverterZone::class,
    ConverterGoalSet::class,
    ConverterDistanceE::class,
    ConverterTimeE::class,
    ConverterWeightE::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
