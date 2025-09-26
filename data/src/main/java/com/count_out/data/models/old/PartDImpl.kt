package com.count_out.data.models.old

//import com.count_out.data.models.ParameterImpl
//import com.count_out.domain.entity.enums.PartName
//import com.count_out.domain.entity.enums.Units
//import com.count_out.domain.entity.workout.Part
//import com.count_out.domain.entity.workout.SpeechKit

//data class PartDImpl(
//    override val idPart: Long = 0,
//    override val planId: Long = 0,
//    override val name: PartName = PartName.WorkUp,
//    override val rings: List<RingDImpl> = emptyList(),
//    override val amount: Int = 0,
//    override val duration: ParameterImpl = ParameterImpl(0.0, Units.M),
//    override var speechId: Long = 0,
//    override var speech: SpeechKit? = null,
//): Part {
//    constructor(part: Part): this(
//        part.idPart,
//        part.planId,
//        part.name,
//        part.rings.map { RingDImpl(it) },
//        part.amount,
//        ParameterImpl(part.duration),
//        part.speechId,
//        part.speech,
//    )
//}