package com.count_out.data.models.types_data

import com.count_out.data.models.Data
import com.count_out.data.models.PlanDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.workout.Domain

@JvmInline
value class ListPlanDb(val item: List<PlanDb>): Data {
    override fun toResultData(): ResultData<Data> =
        if (!this.item.isEmpty()) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.RequestFailed())
    override fun toDomain(ind: Int): Domain = object: Domain{}
}