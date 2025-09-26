package com.count_out.presentation.models
//
//import com.count_out.domain.entity.enums.Units
//import com.count_out.domain.entity.workout.Activity
//import com.count_out.domain.entity.workout.Exercise
//import com.count_out.domain.entity.workout.Parameter
//import com.count_out.domain.entity.workout.Set
//import com.count_out.domain.entity.workout.Speech
//
//data class ExerciseImplP(
//    override val idExercise: Long = 0,
//    override val ringId: Long = 0,
//    override val idView: Int = 0,
//    override val activity: Activity? = null,
//    override val activityId: Long = 1,
//    override val speechId: Long = 0,
//    override val sets: List<Set> = emptyList<Set>(),
//    override val amountSet: Int = 0,
//    override val duration: Parameter = ParameterImplP(value = 0.0, unit = Units.M),
//    override val speeches: List<Speech>
//): Exercise {
//    constructor(exercise: Exercise) : this(
//        idExercise = exercise.idExercise,
//        ringId = exercise.ringId,
//        idView = exercise.idView,
//        activity = exercise.activity,
//        activityId = exercise.activityId,
//        speechId = exercise.speechId,
//        speeches = exercise.speeches,
//        sets = exercise.sets,
//        amountSet = exercise.amountSet,
//        duration = exercise.duration
//    )
//    constructor(exercise: Exercise, activity: Activity) : this(
//        idExercise = exercise.idExercise,
//        ringId = exercise.ringId,
//        idView = exercise.idView,
//        activity = activity,
//        activityId = activity.idActivity,
//        speechId = exercise.speechId,
//        speeches = exercise.speeches,
//        sets = exercise.sets,
//        amountSet = exercise.amountSet,
//        duration = exercise.duration
//    )
//}
