package com.count_out.app.old.data.room

//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.count_out.app.data.room.relation.ConverterDistanceE
//import com.count_out.app.data.room.relation.ConverterGoalSet
//import com.count_out.app.data.room.relation.ConverterTimeE
//import com.count_out.app.data.room.relation.ConverterWeightE
//import com.count_out.app.data.room.relation.ConverterZone
//import com.count_out.app.data.room.tables.ActivityDB
//import com.count_out.app.data.room.tables.CountDB
//import com.count_out.app.data.room.tables.ExerciseDB
//import com.count_out.app.data.room.tables.RoundDB
//import com.count_out.app.data.room.tables.SetDB
//import com.count_out.app.data.room.tables.SettingDB
//import com.count_out.app.data.room.tables.SpeechDB
//import com.count_out.app.data.room.tables.SpeechKitDB
//import com.count_out.app.data.room.tables.TemporaryDB
//import com.count_out.app.data.room.tables.TrainingDB
//import com.count_out.app.data.room.tables.WorkoutDB
//
//@Database(entities = [
//    TrainingDB::class,
//    RoundDB::class,
//    ExerciseDB::class,
//    ActivityDB::class,
//    SetDB::class,
//    SpeechDB::class,
//    SpeechKitDB::class,
//    CountDB::class,
//    SettingDB::class,
//    WorkoutDB::class,
//    TemporaryDB::class,
//    ], version = 1, exportSchema = false)
//@TypeConverters(
//    ConverterZone::class,
//    ConverterGoalSet::class,
//    ConverterDistanceE::class,
//    ConverterTimeE::class,
//    ConverterWeightE::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun dataDao(): DataDao
//}
