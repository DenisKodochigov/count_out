package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface PlanSource {
    fun gets(): Flow<ResultSource<TypeSource>>
    fun get(plan: TypeSource): Flow<ResultSource<TypeSource>>
    fun getId(idPlan: TypeSource): Flow<ResultSource<TypeSource>>
    fun copy(plan: TypeSource): ResultSource<TypeSource>
    fun update(plan: TypeSource): ResultSource<TypeSource>
    fun del(plan: TypeSource): ResultSource<TypeSource>
}