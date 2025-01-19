package com.example.data.entity

import com.example.domain.entity.Exercise

data class ExerciseImpl(
    override val idExercise: Long,
    override val roundId: Long,
    override val ringId: Long,
    override val idView: Int,
    override val activity: ActivityImpl?,
    override val activityId: Long,
    override val speechId: Long,
    override val speech: SpeechKitImpl?,
    override val sets: List<SetImpl>
): Exercise