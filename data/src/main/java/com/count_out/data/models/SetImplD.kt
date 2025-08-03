package com.count_out.data.models


import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit

data class SetImplD(
    override val idSet: Long,
    override val name: String,
    override val exerciseId: Long,
    override val speechId: Long,
    override val speech: SpeechKit?,
    override val goal: Goal,
    override val weight: Parameter,
    override val distance: Parameter,
    override val duration: Parameter,
    override val reps: Int,
    override val intensity: Zone,
    override val intervalReps: Double,
    override val intervalDown: Int,
    override val groupCount: String,
    override val rest: Parameter,
): Set{
    constructor(set: Set, exerciseId: Long = set.exerciseId) : this(
        idSet = set.idSet,
        name = set.name,
        exerciseId = exerciseId,
        speechId = set.speechId,
        speech = set.speech,
        goal = set.goal,
        weight = set.weight,
        distance = set.distance,
        duration = set.duration,
        reps = set.reps,
        intensity = set.intensity,
        intervalReps = set.intervalReps,
        intervalDown = set.intervalDown,
        groupCount = set.groupCount,
        rest = set.rest,
    )
    constructor(exerciseId: Long = 0L) : this(
        idSet = 0,
        exerciseId = exerciseId,
        name = "Set 1",
        speechId = 0,
        speech = null,
        goal = Goal.Count,
        weight = ParameterImpl(value = 1.0, unit = Units.KG),
        distance = ParameterImpl(value = 1.0, unit = Units.MT),
        duration = ParameterImpl(value = 1.0, unit = Units.S),
        reps = 10,
        intensity = Zone.Medium,
        intervalReps = 1.0,
        intervalDown = 0,
        groupCount = "",
        rest = ParameterImpl(value = 1.0, unit = Units.S),
    )
}
