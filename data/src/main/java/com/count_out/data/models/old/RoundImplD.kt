package com.count_out.data.models.old

//import com.count_out.data.models.ParameterImpl
//import com.count_out.domain.entity.enums.RoundType
//import com.count_out.domain.entity.enums.Units
//import com.count_out.domain.entity.workout.Exercise
//import com.count_out.domain.entity.workout.Parameter
//import com.count_out.domain.entity.workout.Round
//import com.count_out.domain.entity.workout.SpeechKit

//data class RoundImplD(
//    override val idRound: Long = 0L,
//    override val trainingId: Long = 0L,
//    override val speechId: Long = 0L,
//    override val roundType: RoundType,
//    override val speech: SpeechKit? = null,
//    override val exercise: List<Exercise> = emptyList(),
//    override val amount: Int = 0,
//    override val duration: Parameter = ParameterImpl(0.0, Units.M),
//    override val numberLaps: Int = 1,
//): Round {
//    constructor(round: Round, id: Long = round.idRound, idPlan: Long = round.trainingId): this(
//        idRound = id,
//        trainingId = idPlan,
//        speechId = round.speechId,
//        roundType = round.roundType,
//        speech = round.speech,
//        exercise = round.exercise,
//        amount = round.amount,
//        duration = round.duration,
//        numberLaps = round.numberLaps
//    )
//}