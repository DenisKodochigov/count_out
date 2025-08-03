package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface LastPlanSource {
    fun saveLastPlan(id: TypeSource): Flow<ResultSource<TypeSource>>
    fun getLastPlan(): Flow<ResultSource<TypeSource>>
}