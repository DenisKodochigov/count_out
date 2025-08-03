package com.count_out.framework.room.db.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.ActivityImpl
import com.count_out.data.models.ExerciseImplD
import com.count_out.framework.room.db.activity.ActivityTable

@Entity(tableName = "tb_exercise")
data class ExerciseTable(
    @PrimaryKey(autoGenerate = true) var idExercise: Long = 0L,
    var roundId: Long = 0,
    var ringId: Long = 0,
    var speechId: Long = 0,
    var activityId: Long = 0,
    var idView: Int = 0,
){
    constructor(item: ExerciseImplD): this(
        idExercise = item.idExercise,
        roundId = item.roundId,
        ringId = item.ringId,
        speechId = item.speechId,
        activityId = item.activityId,
        idView = item.idView,
    )
    constructor(item: ExerciseImplD, speechId: Long = item.speechId, idExercise: Long = item.idExercise): this(
        idExercise = idExercise,
        roundId = item.roundId,
        ringId = item.ringId,
        speechId = speechId,
        activityId = item.activityId,
        idView = item.idView,
    )
}