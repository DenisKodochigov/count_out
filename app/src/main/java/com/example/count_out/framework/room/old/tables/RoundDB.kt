package com.example.count_out.framework.room.old.tables

//import androidx.room.Entity
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
//import com.example.count_out.entity.enums.RoundType
//import com.example.count_out.entity.workout.speech.SpeechKit
//import com.example.count_out.entity.workout.Exercise
//import com.example.count_out.entity.workout.Parameter
//import com.example.count_out.entity.workout.Round
//
//@Entity(tableName = "tb_round")
//data class RoundDB(
//    @PrimaryKey(autoGenerate = true) override var idRound: Long = 0L,
//    override var trainingId: Long = 0,
//    override var speechId: Long = 0,
//    override var roundType: RoundType = RoundType.WorkOut,
//    @Ignore override val exercise: List<Exercise> = emptyList(),
//    @Ignore override var speech: SpeechKit = SpeechKitDB(),
//    @Ignore override val amount: Int,
//    @Ignore override val duration: Parameter,
//): Round