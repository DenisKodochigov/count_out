package com.count_out.data.models

import com.count_out.data.models.throwable.ResultData
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.SpeechKit

abstract class PlanDb: Data {
    abstract val idPlan: Long
    abstract val name: String
    abstract val speeches: List<SpeechDb>
    abstract val parts: List<PartDb>
    abstract val amountActivity: Int
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Plan {
        override val idPlan: Long = this@PlanDb.idPlan
        override val name: String = this@PlanDb.name
        override val amountActivity: Int = this@PlanDb.amountActivity
        override val parts: List<Part> = this@PlanDb.parts.mapIndexed{ind,item -> item.toDomain(ind) as Part }
        override val speechKit: SpeechKit = SpeechKit.fill(this@PlanDb.speeches.map{it.toDomain()})
    }
}