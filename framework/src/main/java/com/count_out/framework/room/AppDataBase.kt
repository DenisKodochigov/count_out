package com.count_out.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTable
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTable
import com.count_out.framework.room.db.relation.ConverterDistanceE
import com.count_out.framework.room.db.relation.ConverterGoalSet
import com.count_out.framework.room.db.relation.ConverterTimeE
import com.count_out.framework.room.db.relation.ConverterWeightE
import com.count_out.framework.room.db.relation.ConverterZone
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTable
import com.count_out.framework.room.db.round.RoundDao
import com.count_out.framework.room.db.round.RoundTable
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTable
import com.count_out.framework.room.db.settings.SettingDao
import com.count_out.framework.room.db.settings.SettingTable
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTable
import com.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.count_out.framework.room.db.speech_kit.SpeechKitTable
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.db.training.TrainingTable
import com.count_out.framework.room.db.traking.TemporaryTable
import com.count_out.framework.room.db.traking.TrackingDao
import com.count_out.framework.room.db.traking.WorkoutTable

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
@TypeConverters(
    ConverterZone::class,
    ConverterGoalSet::class,
    ConverterDistanceE::class,
    ConverterTimeE::class,
    ConverterWeightE::class)
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

