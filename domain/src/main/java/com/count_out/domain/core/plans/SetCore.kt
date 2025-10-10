package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetCore @Inject constructor(private val repo: SetRepo): Core()  {
    fun copy(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.copy(set) }
    fun del(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.del(set) }
    fun update(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.update(set) }
}