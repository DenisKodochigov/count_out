package com.count_out.data.models.types_data

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.domain.entity.workout.Domain

data class NameIdDb(
    val name: String,
    val id: Long
): Data {
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Domain {}
}