package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.types_domai.StringDm
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class StringDb(val item: String): Data {
    fun toResultData(): ResultData<Data> =
        if (this.item.isNotEmpty()) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.ErrorString())
    override fun toDomain(ind: Int): Domain = StringDm(item = this.item)
    companion object {
        fun fromDomain(domain: Domain): StringDb {
            return when (domain) {
                is StringDm -> StringDb(domain.item)
                else -> throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
}