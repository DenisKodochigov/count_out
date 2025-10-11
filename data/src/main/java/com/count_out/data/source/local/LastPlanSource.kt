package com.count_out.data.source.local

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface LastPlanSource {
    fun saveLastPlan(id: Data): Flow<ResultData<Data>>
    fun getLastPlan(): Flow<ResultData<Data>>
}