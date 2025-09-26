package com.count_out.data.models.old

//import com.count_out.data.models.ParameterImpl
//import com.count_out.domain.entity.enums.Units
//import com.count_out.domain.entity.workout.Exercise
//import com.count_out.domain.entity.workout.Parameter
//import com.count_out.domain.entity.workout.Ring
//import com.count_out.domain.entity.workout.SpeechKit
//
//data class RingDImpl(
//    override val idRing: Long = 0L,
//    override val partId: Long = 0L,
//    override val speechId: Long = 0L,
//    override val speech: SpeechKit? = null,
//    override val exercises: List<Exercise> = emptyList(),
//    override val amount: Int = 0,
//    override val duration: Parameter = ParameterImpl(0.0, Units.M),
//    override val numberLaps: Int = 1,
//): Ring {
//    constructor(ring: Ring, id: Long = ring.idRing, idPlan: Long = ring.partId): this(
//        idRing = id,
//        partId = idPlan,
//        speechId = ring.speechId,
//        speech = ring.speech,
//        exercises = ring.exercises,
//        amount = ring.amount,
//        duration = ring.duration,
//        numberLaps = ring.numberLaps
//    )
//}