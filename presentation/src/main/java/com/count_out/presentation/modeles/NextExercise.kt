package com.count_out.presentation.modeles

import com.count_out.domain.entity.NextExercise

data class NextExerciseImpl(
    override val nextActivityName: String = "",
    override val nextExerciseId: Long = 0,
    override val nextExerciseQuantitySet: Int = 0,
    override val nextExerciseSummarizeSet: List<Pair<String, Int>> = emptyList(),
): NextExercise
