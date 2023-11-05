package com.example.count_out.data.room.tables

import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set

data class SetDB(
    override val name: String = "",
    override val exercise: Exercise = ExerciseDB(),
    override val reps: Int = 0,
    override val duration: Int = 0,
    override val countdown: Boolean = true,
    override val weight: Int = 0,
    override val intervalReps: Int = 0
): Set
