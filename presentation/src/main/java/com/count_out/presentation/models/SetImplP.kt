package com.count_out.presentation.models

import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit

data class SetImplP(
    override val idSet: Long = 0,
    override val name: String = "",
    override val exerciseId: Long = 0,
    override val speechId: Long = 0,
    override val goal: Goal = Goal.Duration,
    override val weight: Parameter = ParameterImplP(value = 0.0, unit = Units.GR),
    override val distance: Parameter = ParameterImplP(value = 0.0, unit = Units.MT),
    override val duration: Parameter = ParameterImplP(value = 0.0, unit = Units.M),
    override val reps: Int = 0,
    override val intensity: Zone = Zone.Medium,
    override val intervalReps: Double = 0.0,
    override val intervalDown: Int = 0,
    override val groupCount: String = "",
    override val rest: Parameter = ParameterImplP(value = 0.0, unit = Units.M),
    var positions: Pair<Int, Int> = Pair(0 , 0),
    override val speechKit: SpeechKit = SpeechKit.EMPTY,
): Set {
    constructor(set: Set) : this(
        idSet = set.idSet,
        name = set.name,
        exerciseId = set.exerciseId,
        speechId = set.speechId,
        speechKit = set.speechKit,
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
        positions = Pair(0 , 0)
    )
    constructor(set: Set, position: Pair<Int, Int>) : this(
        idSet = set.idSet,
        name = set.name,
        exerciseId = set.exerciseId,
        speechId = set.speechId,
        speechKit = set.speechKit,
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
        positions = position
    )
}
