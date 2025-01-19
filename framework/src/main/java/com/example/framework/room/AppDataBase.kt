package com.example.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.framework.room.db.activity.ActivityDao
import com.example.framework.room.db.activity.ActivityTable
import com.example.framework.room.db.exercise.ExerciseDao
import com.example.framework.room.db.exercise.ExerciseTable
import com.example.framework.room.db.relation.ConverterDistanceE
import com.example.framework.room.db.relation.ConverterGoalSet
import com.example.framework.room.db.relation.ConverterTimeE
import com.example.framework.room.db.relation.ConverterWeightE
import com.example.framework.room.db.relation.ConverterZone
import com.example.framework.room.db.round.RoundDao
import com.example.framework.room.db.round.RoundTable
import com.example.framework.room.db.set.SetDao
import com.example.framework.room.db.set.SetTable
import com.example.framework.room.db.settings.SettingDao
import com.example.framework.room.db.settings.SettingTable
import com.example.framework.room.db.speech.SpeechDao
import com.example.framework.room.db.speech.SpeechKitTable
import com.example.framework.room.db.speech.SpeechTable
import com.example.framework.room.db.training.TrainingDao
import com.example.framework.room.db.training.TrainingTable
import com.example.framework.room.db.traking.TemporaryTable
import com.example.framework.room.db.traking.TrackingDao
import com.example.framework.room.db.traking.WorkoutTable

@Database(entities = [
    TrainingTable::class,
    RoundTable::class,
    ExerciseTable::class,
    ActivityTable::class,
    SetTable::class,
    SpeechTable::class,
    SpeechKitTable::class,
    SettingTable::class,
    WorkoutTable::class,
    TemporaryTable::class,
], version = 1, exportSchema = false)
@TypeConverters(
    ConverterZone::class,
    ConverterGoalSet::class,
    ConverterDistanceE::class,
    ConverterTimeE::class,
    ConverterWeightE::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun roundDao(): RoundDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun activityDao(): ActivityDao
    abstract fun setDao(): SetDao
    abstract fun speechDao(): SpeechDao
    abstract fun settingDao(): SettingDao
    abstract fun trackingDao(): TrackingDao
}

