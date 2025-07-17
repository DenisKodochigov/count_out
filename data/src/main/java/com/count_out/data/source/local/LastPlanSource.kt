package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultSource
import kotlinx.coroutines.flow.Flow

interface LastPlanSource {
    fun saveLastPlan(id: Long): Flow<ResultSource<Boolean>>
    fun getLastPlan(): Flow<ResultSource<Long>>
}