package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource1
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface PlanSource {
    fun gets(): Flow<ResultSource<Data>>
    fun get(plan: TypeSource): Flow<ResultSource<Data>>
    fun getId(idPlan: TypeSource): Flow<ResultSource<Data>>
    fun copy(plan: TypeSource): ResultSource<Data>
    fun update(nameId: TypeSource): ResultSource<Data>
    fun del(plan: TypeSource): ResultSource<Data>
}