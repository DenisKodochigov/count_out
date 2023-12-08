package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set
@Entity(tableName = "tb_set")
data class SetDB(
    @PrimaryKey(autoGenerate = true) override val idSet: Long = 0L,
    override val roundId: Long = 0L,
    override val name: String = "",
    override val exercise: Exercise = ExerciseDB(),
    override val reps: Int = 0,
    override val duration: Int = 0,
    override val countdown: Boolean = true,
    override val weight: Int = 0,
    override val intervalReps: Int = 0,
): Set
