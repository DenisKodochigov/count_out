//package com.count_out.app.presentation.models
//
//import com.count_out.domain.entity.Activity
//import com.count_out.domain.entity.Exercise
//import com.count_out.domain.entity.Set
//import com.count_out.domain.entity.SpeechKit
//
//data class ExerciseImpl (
//    override val idExercise: Long = 0,
//    override val roundId: Long = 0,
//    override val ringId: Long = 0,
//    override val idView: Int = 0,
//    override val activity: Activity? = null,
//    override val activityId: Long = 1,
//    override val speechId: Long = 0,
//    override val speech: SpeechKit? = null,
//    override val sets: List<Set> = emptyList<Set>(),
//    override val amountSet: Int = 0,
//    override val duration: Int = 0
//
//): Exercise