package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.PartRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartCore @Inject constructor(private val repo: PartRepo): Core() {
    fun update(round: Domain): Flow<ResultDomain<Domain>> {
        return repo.update(round) }
    fun del(round: Domain): Flow<ResultDomain<Domain>> {
        return repo.del(round) }
}
