package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Round
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.Speech

@Entity(tableName = "tb_round")
data class RoundDB(
    @PrimaryKey(autoGenerate = true) override var idRound: Long = 0L,
    override var trainingId: Long = 0,
    override var speechId: Long = 0,
    @Ignore override val roundType: RoundType = RoundType.OUT,
    @Ignore override val exercise: MutableList<Exercise> = mutableListOf(),
    @Ignore override var speech: Speech = SpeechDB(),
): Round
