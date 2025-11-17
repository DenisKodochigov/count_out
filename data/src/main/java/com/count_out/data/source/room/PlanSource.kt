package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface PlanSource {
    fun gets(): Flow<ResultData<Data>>
    fun get(idPlan: Data): Flow<ResultData<Data>>
    fun getId(idPlan: Data): Flow<ResultData<Data>>
    fun copy(plan: Data): ResultData<Data>
    fun update(nameId: Data): ResultData<Data>
    fun del(plan: Data): ResultData<Data>
    fun changeSequence(setViewId: Data): ResultData<Data>
}