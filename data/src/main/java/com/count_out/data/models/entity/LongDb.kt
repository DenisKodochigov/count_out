package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class LongDb(val item: Long): Data {
    fun toResultData(): ResultData<Data> =
        if (this.item > 0L) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.ErrorLong())
    override fun toDomain(ind: Int): Domain = object: Domain {}
    companion object {
        fun fromDomain(domain: Domain): LongDb {
            return when (domain) {
                is LongDm -> LongDb(domain.item)
                else -> throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
}