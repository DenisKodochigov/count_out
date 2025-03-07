package com.example.count_out.entity.models

import com.example.count_out.entity.enums.Units
import com.example.count_out.entity.workout.Activity
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Parameter
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.speech.SpeechKit

data class ExerciseImpl (
    override val idExercise: Long = 0,
    override val roundId: Long = 0,
    override val ringId: Long = 0,
    override val idView: Int = 0,
    override val activity: Activity? = null,
    override val activityId: Long = 1,
    override val speechId: Long = 0,
    override val speech: SpeechKit? = null,
    override val sets: List<Set> = emptyList(),
    override val amountSet: Int = 0,
    override val duration: Parameter = ParameterImpl(0.0, Units.M)
): Exercise