//package com.count_out.app.presentation.models
//
//import com.count_out.entity.entity.workout.Parameter
//import com.count_out.entity.entity.workout.Set
//import com.count_out.entity.entity.workout.SpeechKit
//import com.count_out.domain.entity.enums.Goal
//import com.count_out.domain.entity.enums.Units
//import com.count_out.domain.entity.enums.Zone
//
//data class SetImpl(
//    override val idSet: Long = 0,
//    override val name: String = "",
//    override val exerciseId: Long = 0,
//    override val speechId: Long = 0,
//    override val speech: SpeechKit? = null,
//    override val goal: Goal = Goal.Duration,
//    override val weight: Parameter = ParameterImpl(value = 0.0, unit = Units.GR),
//    override val distance: Parameter = ParameterImpl(value = 0.0, unit = Units.MT),
//    override val duration: Parameter = ParameterImpl(value = 0.0, unit = Units.M),
//    override val reps: Int = 0,
//    override val intensity: Zone = Zone.Medium,
//    override val intervalReps: Double = 0.0,
//    override val intervalDown: Int = 0,
//    override val groupCount: String = "",
//    override val rest: Parameter = ParameterImpl(value = 0.0, unit = Units.M),
//    var positions: Pair<Int, Int> = 0 to 0
//): Set
