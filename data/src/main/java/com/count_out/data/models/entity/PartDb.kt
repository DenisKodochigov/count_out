package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.SpeechKit

abstract class PartDb: Data {
    abstract val idPart: Long
    abstract val planId: Long
    abstract val speeches: List<SpeechDb>
    abstract val rings: List<RingDb>
    abstract val amount: Int
    abstract val duration: Double
//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Part{
        override val idPart: Long = this@PartDb.idPart
        override val planId: Long = this@PartDb.planId
        override val name: PartName = PartName.entries[ind]
        override val rings: List<Ring> = this@PartDb.rings.map { it.toDomain() as Ring }
        override val amount: Int = this@PartDb.amount
        override val duration: Parameter = Parameter.fill(this@PartDb.duration, 2)
        override val speechKit: SpeechKit = SpeechKit.fill(this@PartDb.speeches.map{it.toDomain()})
    }

    companion object {
        fun fromDomain(domain: Domain): PartDb {
            return when (domain) {
                is Part -> object: PartDb(){
                    override val idPart: Long = domain.idPart
                    override val planId: Long = domain.planId
                    override val speeches: List<SpeechDb> = domain.speechKit.toList().map { SpeechDb.fromDomain(it) }
                    override val rings: List<RingDb> = domain.rings.map { RingDb.fromDomain(it)}
                    override val amount: Int = domain.amount
                    override val duration: Double = domain.duration.value
                }
                else -> throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
}