package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class BooleanDb(val item: Boolean): Data {
    fun toResultData(): ResultData<Data> =
        if (this.item) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.ErrorBoolean())
    override fun toDomain(ind: Int): Domain = object: Domain {}
}