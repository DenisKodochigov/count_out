package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultDataSource
import kotlinx.coroutines.flow.Flow

interface LastPlanSource {
    fun saveLastPlan(id: Long): Flow<ResultDataSource<Boolean>>
    fun getLastPlan(): Flow<ResultDataSource<Long>>
}