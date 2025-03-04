package com.count_out.data.router.models

import com.count_out.entity.entity.workout.Exercise
import com.count_out.entity.entity.NextExercise
import com.count_out.entity.entity.workout.Round
import com.count_out.entity.entity.workout.Set
import com.count_out.entity.entity.StepTraining

data class StepTrainingImpl(
    override val round: Round?,
    override val exercise: Exercise?,
    override val nextExercise: NextExercise?,
    override val numberExercise: Int,
    override val quantityExercise: Int,
    override var currentSet: Set?,
    override val numberSet: Int,
    override val quantitySet: Int
): StepTraining