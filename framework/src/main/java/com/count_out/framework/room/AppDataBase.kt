package com.count_out.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTb
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.old.settings.SettingDao
import com.count_out.framework.room.db.old.settings.SettingTb
import com.count_out.framework.room.db.part.PartDao
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.plan.PlanDao
import com.count_out.framework.room.db.plan.PlanTb
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.traking.TemporaryTb
import com.count_out.framework.room.db.traking.TrackingDao
import com.count_out.framework.room.db.traking.TrackingTb

@Database(entities = [
    PlanTb::class,
    PartTb::class,
    RingTb::class,
    ExerciseTb::class,
    ActivityTb::class,
    SetTb::class,
    SpeechTb::class,
    SettingTb::class,
    TrackingTb::class,
    TemporaryTb::class,
], version = 1, exportSchema = false)
//@TypeConverters(
//    ConverterZone::class,
//    ConverterGoalSet::class,
//    ConverterDistanceE::class,
//    ConverterTimeE::class,
//    ConverterWeightE::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun partDao(): PartDao
    abstract fun ringDao(): RingDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun activityDao(): ActivityDao
    abstract fun setDao(): SetDao
    abstract fun speechDao(): SpeechDao
    abstract fun settingDao(): SettingDao
    abstract fun trackingDao(): TrackingDao
}

