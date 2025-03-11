package com.count_out.data.models

import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Set

data class ExerciseImpl(
    override val idExercise: Long,
    override val roundId: Long,
    override val ringId: Long,
    override val idView: Int,
    override val activity: ActivityImpl?,
    override val activityId: Long,
    override val speechId: Long,
    override val speech: SpeechKit?,
    override val sets: List<Set>,
    override val amountSet: Int,
    override val duration: Int
): Exercise