package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Round
import com.example.count_out.entity.SpeechActivity

@Entity(tableName = "tb_round")
data class RoundDB(
    @PrimaryKey(autoGenerate = true) override val idRound: Long = 0L,
    override val trainingId: Long = 0,
    override val speechId: Long = 0,
    @Ignore override val exercise: List<Exercise> = emptyList(),
    @Ignore override val speechActivity: SpeechActivity = SpeechActivityDB(),
): Round
