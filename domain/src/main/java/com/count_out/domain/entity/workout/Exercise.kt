package com.count_out.domain.entity.workout

data class Exercise (
     val idExercise: Long = 0,
     val roundId: Long = 0,
     val ringId: Long = 0,
     val idView: Int = 0,
     val activity: Activity? = null,
     val activityId: Long = 1,
     val speechId: Long = 0,
     val speech: SpeechKit? = null,
     val sets: List<Set> = emptyList<Set>(),
     val amountSet: Int = 0,
     val duration: Int = 0
): Element