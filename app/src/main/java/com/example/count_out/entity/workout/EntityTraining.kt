package com.example.count_out.entity.workout

import com.example.count_out.entity.ui.NextExercise

data class EntityTraining (
    val exercise: Exercise? = null,
    val nextExercise: NextExercise? = null,
    val exerciseNumber: Int = 0,
    val round: Round? = null,
    val roundCurrent: Int = 0,
    val set: Set? = null,
    val setNumber: Int = 0,
    val quantitySet: Int = 0,
    val setExercise: Int = 0,

    )