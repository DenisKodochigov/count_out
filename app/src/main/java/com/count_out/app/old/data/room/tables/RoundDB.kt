//package com.count_out.app.data.room.tables
//
//import androidx.room.Entity
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
//import com.count_out.app.entity.RoundType
//import com.count_out.app.entity.speech.SpeechKit
//import com.count_out.app.entity.workout.Exercise
//import com.count_out.app.entity.workout.Round
//
//@Entity(tableName = "tb_round")
//data class RoundDB(
//    @PrimaryKey(autoGenerate = true) override var idRound: Long = 0L,
//    override var trainingId: Long = 0,
//    override var countRing: Int = 0,
//    override var speechId: Long = 0,
//    override var roundType: RoundType = RoundType.OUT,
//    @Ignore override val exercise: List<Exercise> = emptyList(),
//    @Ignore override var speech: SpeechKit = SpeechKitDB(),
//): Round