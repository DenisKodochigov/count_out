//package com.count_out.app.data.room.tables
//
//import androidx.room.Entity
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
//import com.count_out.app.entity.speech.SpeechKit
//import com.count_out.app.entity.workout.Round
//import com.count_out.app.entity.workout.Training
//
//@Entity(tableName = "tb_trainings")
//data class TrainingDB(
//    @PrimaryKey(autoGenerate = true) override var idTraining: Long = 0L,
//    override var name: String = "",
//    override var isSelected: Boolean = false,
//    override var speechId: Long = 0,
//    @Ignore override var amountActivity: Int = 0,
//    @Ignore override var speech: SpeechKit = SpeechKitDB(),
//    @Ignore override var rounds: List<Round> = emptyList(),
//) : Training
