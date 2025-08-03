package com.count_out.domain.repository

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface LastPlanRepo {
    fun getLastUsedPlan(): Flow<ResultUC<TypeRepo>>
    fun saveLastUsedPlan(id: TypeRepo): Flow<ResultUC<TypeRepo>>
}