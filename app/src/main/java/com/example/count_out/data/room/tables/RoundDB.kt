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
    override val workType: Int,
    override val trainingId: Long,
    override val speechId: Long,
    @Ignore override val exercise: List<Exercise>,
    @Ignore override val speechActivity: SpeechActivity,
): Round
