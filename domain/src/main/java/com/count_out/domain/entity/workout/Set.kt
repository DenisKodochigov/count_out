package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone

interface Set: Domain {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechKit: SpeechKit
    val goal: Goal
    val weight: Parameter
    val distance: Parameter
    val duration: Parameter
    val reps: Int // количество отстчетов
    val intensity: Zone
    val intervalReps: Double
    val intervalDown: Int //замедление отчетов
    val groupCount: String // Группы отстчетов
    val rest: Parameter //Храним значение в секундах
    companion object {
        fun Set.copy(
            idSet: Long = this@copy.idSet,
            goal: Goal = this@copy.goal,
            distance: Parameter = this@copy.distance,
            duration: Parameter = this@copy.duration,
            weight: Parameter = this@copy.weight,
            rest: Parameter = this@copy.rest,
            reps: Int = this@copy.reps,
            intervalReps: Double = this@copy.intervalReps,
            groupCount: String = this@copy.groupCount,
            intensity: Zone = this@copy.intensity,
            name: String = this@copy.name,
            exerciseId: Long = this@copy.exerciseId
        ) = object: Set{
            override val idSet: Long = idSet
            override val name: String = name
            override val exerciseId: Long = exerciseId
            override val speechKit: SpeechKit = this@copy.speechKit
            override val goal: Goal = goal
            override val weight: Parameter = weight
            override val distance: Parameter = distance
            override val duration: Parameter = duration
            override val reps: Int = reps
            override val intensity: Zone = intensity
            override val intervalReps: Double = intervalReps
            override val intervalDown: Int = this@copy.intervalDown
            override val groupCount: String = groupCount
            override val rest: Parameter = rest
        }
        fun default(
            idSet: Long = 0,
            goal: Goal = Goal.Count,
            distance: Parameter = Parameter.fill(value = 0.0, unit = Units.MT.ordinal),
            duration: Parameter = Parameter.fill(value = 0.0, unit = Units.M.ordinal),
            weight: Parameter = Parameter.fill(value = 0.0, unit = Units.GR.ordinal),
            rest: Parameter = Parameter.fill(value = 0.0, unit = Units.S.ordinal),
            reps: Int = 0,
            intervalReps: Double = 0.0,
            groupCount: String = "",
            intensity: Zone = Zone.Medium,
            name: String = "",
            exerciseId: Long = 0,
        ) = object: Set{
            override val idSet: Long = idSet
            override val name: String = name
            override val exerciseId: Long = exerciseId
            override val speechKit: SpeechKit = SpeechKit.EMPTY
            override val goal: Goal = goal
            override val weight: Parameter = weight
            override val distance: Parameter = distance
            override val duration: Parameter = duration
            override val reps: Int = reps
            override val intensity: Zone = intensity
            override val intervalReps: Double = intervalReps
            override val intervalDown: Int = 0
            override val groupCount: String = groupCount
            override val rest: Parameter = rest
        }
    }
}
