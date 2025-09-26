package com.count_out.data.models.old

import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Speech

//data class PlanDImpl(
//    override val idPlan: Long = 0L,
//    override val name: String = "",
//    override val amountActivity: Int = 0,
//    override var speechId: Long = 0L,
//    override var speeches: List<Speech> = emptyList(),
//    override val parts: List<Part> = emptyList(),
//): Plan {
//    constructor(tr: Plan): this(
//        idPlan = tr.idPlan,
//        name = tr.name,
//        amountActivity = tr.amountActivity,
//        parts = tr.parts,
//        speechId = tr.speechId,
//        speeches = tr.speeches,
//    )
//}