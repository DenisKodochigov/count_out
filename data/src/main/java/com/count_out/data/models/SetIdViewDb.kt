package com.count_out.data.models

import com.count_out.data.models.throwable.ResultData
import com.count_out.domain.entity.workout.Domain

abstract class SetIdViewDb: Data {
    abstract val ringId: Long
    abstract val from: Int
    abstract val to: Int
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Domain{}
}