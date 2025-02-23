package com.count_out.data.router.models

import com.count_out.domain.entity.Exercise
import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.Round
import com.count_out.domain.entity.Set
import com.count_out.domain.entity.StepTraining

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