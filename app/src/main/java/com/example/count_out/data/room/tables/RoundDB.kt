package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.Round
import com.example.count_out.entity.Set
@Entity(tableName = "tb_round")
data class RoundDB(
    @PrimaryKey(autoGenerate = true) override val idRound: Long = 0L,
    override val workoutId: Long = 0L,
    override val set: Set = SetDB(),
    override val amount: Int = 10,
    override val beforeTime: Int = 5,
    override val afterTime: Int = 5,
    override val restTime: Int = 4,
    override val open: Boolean = false,
): Round
