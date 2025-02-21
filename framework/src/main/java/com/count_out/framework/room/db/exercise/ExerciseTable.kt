package com.count_out.framework.room.db.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_exercise")
data class ExerciseTable(
    @PrimaryKey(autoGenerate = true) var idExercise: Long = 0L,
    var roundId: Long = 0,
    var ringId: Long = 0,
    var speechId: Long = 0,
    var activityId: Long = 0,
    var idView: Int = 0,
)