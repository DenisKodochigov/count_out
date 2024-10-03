package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.SpeechKit
import com.example.count_out.entity.workout.Training
@Entity(tableName = "tb_trainings")
data class TrainingDB(
    @PrimaryKey(autoGenerate = true) override var idTraining: Long = 0L,
    override var name: String = "",
    override var isSelected: Boolean = false,
    override var speechId: Long = 0,
    @Ignore override var amountActivity: Int = 0,
    @Ignore override var speech: SpeechKit = SpeechKitDB(),
    @Ignore override var rounds: List<Round> = emptyList(),
) : Training

