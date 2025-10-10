package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityCore @Inject constructor(private val repo: ActivityRepo): Core() {
    fun gets(): Flow<ResultDomain<Domain>> {
        return repo.gets() }
    fun get(request: Domain): Flow<ResultDomain<Domain>> {
        return repo.get(request) }
    fun copy(request: Domain): Flow<ResultDomain<Domain>> {
        return repo.copy(request) }
    fun update(request: Domain): Flow<ResultDomain<Domain>> {
        return repo.update(request) }
    fun del(request: Domain): Flow<ResultDomain<Domain>> {
        return repo.del(request) }
}