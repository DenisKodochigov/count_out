package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.LastPlanRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LastPlanCore @Inject constructor(private val repo: LastPlanRepo): Core() {
    fun getLastUsedPlan(): Flow<ResultDomain<Domain>>{
        return repo.getLastUsedPlan() }
    fun saveLastUsedPlan(id: Domain): Flow<ResultDomain<Domain>> {
        return repo.saveLastUsedPlan(id) }
}