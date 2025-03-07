package com.example.count_out.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.count_out.framework.room.db.traking.TemporaryTable
import com.count_out.framework.room.db.traking.TrackingDao
import com.count_out.framework.room.db.traking.WorkoutTable
import com.example.count_out.framework.room.db.activity.ActivityDao
import com.example.count_out.framework.room.db.activity.ActivityTable
import com.example.count_out.framework.room.db.exercise.ExerciseDao
import com.example.count_out.framework.room.db.exercise.ExerciseTable
import com.example.count_out.framework.room.db.ring.RingDao
import com.example.count_out.framework.room.db.ring.RingTable
import com.example.count_out.framework.room.db.round.RoundDao
import com.example.count_out.framework.room.db.round.RoundTable
import com.example.count_out.framework.room.db.set.SetDao
import com.example.count_out.framework.room.db.set.SetTable
import com.example.count_out.framework.room.db.settings.SettingDao
import com.example.count_out.framework.room.db.settings.SettingTable
import com.example.count_out.framework.room.db.speech.SpeechDao
import com.example.count_out.framework.room.db.speech.SpeechTable
import com.example.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.example.count_out.framework.room.db.speech_kit.SpeechKitTable
import com.example.count_out.framework.room.db.training.TrainingDao
import com.example.count_out.framework.room.db.training.TrainingTable

@Database(entities = [
    TrainingTable::class,
    RoundTable::class,
    RingTable::class,
    ExerciseTable::class,
    ActivityTable::class,
    SetTable::class,
    SpeechTable::class,
    SpeechKitTable::class,
    SettingTable::class,
    WorkoutTable::class,
    TemporaryTable::class,
], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun ringDao(): RingDao
    abstract fun roundDao(): RoundDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun activityDao(): ActivityDao
    abstract fun setDao(): SetDao
    abstract fun speechDao(): SpeechDao
    abstract fun speechKitDao(): SpeechKitDao
    abstract fun settingDao(): SettingDao
    abstract fun trackingDao(): TrackingDao
}

