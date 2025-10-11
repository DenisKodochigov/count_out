package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.SpeechKit

abstract class PartDb: Data {
    abstract val idPart: Long
    abstract val planId: Long
    abstract val speeches: List<SpeechDb>
    abstract val rings: List<RingDb>
    abstract val amount: Int
    abstract val duration: Double
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Part{
        override val idPart: Long = this@PartDb.idPart
        override val planId: Long = this@PartDb.planId
        override val name: PartName = PartName.entries[ind]
        override val rings: List<Ring> = this@PartDb.rings.map { it.toDomain() as Ring }
        override val amount: Int = this@PartDb.amount
        override val duration: Parameter = Parameter.fill(this@PartDb.duration, 2)
        override val speechKit: SpeechKit = SpeechKit.fill(this@PartDb.speeches.map{it.toDomain()})
    }
}