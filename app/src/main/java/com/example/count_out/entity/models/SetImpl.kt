package com.example.count_out.entity.models

import com.example.count_out.entity.enums.Goal
import com.example.count_out.entity.enums.Units
import com.example.count_out.entity.enums.Zone
import com.example.count_out.entity.workout.Parameter
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.speech.SpeechKit
import com.example.count_out.entity.models.ParameterImpl

data class SetImpl(
    override val idSet: Long = 0,
    override val name: String = "",
    override val exerciseId: Long = 0,
    override val speechId: Long = 0,
    override val speech: SpeechKit? = null,
    override val goal: Goal = Goal.Duration,
    override val weight: Parameter = ParameterImpl(value = 0.0, unit = Units.GR),
    override val distance: Parameter = ParameterImpl(value = 0.0, unit = Units.MT),
    override val duration: Parameter = ParameterImpl(value = 0.0, unit = Units.M),
    override val reps: Int = 0,
    override val intensity: Zone = Zone.Medium,
    override val intervalReps: Double = 0.0,
    override val intervalDown: Int = 0,
    override val groupCount: String = "",
    override val rest: Parameter = ParameterImpl(value = 0.0, unit = Units.M),
    var positions: Pair<Int, Int> = Pair(0 , 0),
): Set
