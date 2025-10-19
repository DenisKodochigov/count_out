package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.types_domai.PlansDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan

@JvmInline
value class PlansDb(val item: List<PlanDb>): Data {
    fun toResultData(): ResultData<PlansDb> =
        if (this.item.isNotEmpty()) ResultData.Success(this)
        else ResultData.Error(ThrowableDS.RequestFailed())
    override fun toDomain(ind: Int): Domain = PlansDm(item.map { it.toDomain(0) as Plan })
}