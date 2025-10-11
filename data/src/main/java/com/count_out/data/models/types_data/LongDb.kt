package com.count_out.data.models.types_data

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class LongDb(val item: Long): Data{
    override fun toResultData(): ResultData<Data> =
        if (this.item > 0L) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.RequestFailed())
    override fun toDomain(ind: Int): Domain = object: Domain{}
}