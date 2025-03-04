package com.count_out.data.router.models

import com.count_out.entity.entity.NextExercise

data class NextExerciseImpl(
    override val nextActivityName: String,
    override val nextExerciseId: Long,
    override val nextExerciseQuantitySet: Int,
    override val nextExerciseSummarizeSet: List<Pair<String, Int>>
): NextExercise