package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.PlanRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlanCore @Inject constructor(private val repo: PlanRepo): Core()  {
    fun get(training: Domain): Flow<ResultDomain<Domain>>{
        return repo.get(training) }
    fun gets(): Flow<ResultDomain<Domain>>{
        return repo.gets() }
    fun del(training: Domain): Flow<ResultDomain<Domain>>{
        return repo.del(training) }
    fun copy(training: Domain): Flow<ResultDomain<Domain>>{
        return repo.copy(training) }
    fun update(training: Domain): Flow<ResultDomain<Domain>>{
        return repo.update(training) }

}