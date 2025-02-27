//package com.count_out.app.data.room.tables
//
//import androidx.room.Entity
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
//import com.count_out.app.entity.speech.SpeechKit
//import com.count_out.app.entity.workout.Activity
//import com.count_out.app.entity.workout.Exercise
//import com.count_out.app.entity.workout.Set
//
//@Entity(tableName = "tb_exercise")
//data class ExerciseDB(
//    @PrimaryKey(autoGenerate = true) override var idExercise: Long = 0L,
//    override var roundId: Long = 0,
//    override var speechId: Long = 0,
//    override var activityId: Long = 0,
//    override var idView: Int = 0,
//    @Ignore override var speech: SpeechKit = SpeechKitDB(),
//    @Ignore override var activity: Activity = ActivityDB(),
//    @Ignore override var sets: List<Set> = emptyList(),
//): Exercise
