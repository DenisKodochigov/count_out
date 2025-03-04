package com.count_out.data.models

import com.count_out.entity.entity.workout.Exercise

data class ExerciseImpl(
    override val idExercise: Long,
    override val roundId: Long,
    override val ringId: Long,
    override val idView: Int,
    override val activity: ActivityImpl?,
    override val activityId: Long,
    override val speechId: Long,
    override val speech: SpeechKitImpl?,
    override val sets: List<SetImpl>,
    override val amountSet: Int,
    override val duration: Int
): Exercise