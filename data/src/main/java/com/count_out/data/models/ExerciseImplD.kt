package com.count_out.data.models

import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit

data class ExerciseImplD (
    override val idExercise: Long = 0,
    override val roundId: Long = 0,
    override val ringId: Long = 0,
    override val idView: Int = 0,
    override val activity: Activity? = null,
    override val activityId: Long = 1,
    override val speechId: Long = 0,
    override val speech: SpeechKit? = null,
    override val sets: List<Set> = emptyList<Set>(),
    override val amountSet: Int = 0,
    override val duration: Int = 0
): Exercise{
    constructor(ex: Exercise): this(
       idExercise = ex.idExercise,
       roundId = ex.roundId,
       ringId = ex.ringId,
       idView = ex.idView,
       activity = ex.activity,
       activityId = ex.activityId,
       speechId = ex.speechId,
       speech = ex.speech,
       sets = ex.sets,
       amountSet = ex.amountSet,
       duration = ex.duration
    )
}