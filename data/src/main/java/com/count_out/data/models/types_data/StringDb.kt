package com.count_out.data.models.types_data

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.types_domai.StringDm
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class StringDb(val item: String): Data {
    override fun toResultData(): ResultData<Data> =
        if (this.item.isNotEmpty()) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.RequestFailed())
    override fun toDomain(ind: Int): Domain = StringDm(item = this.item)
}