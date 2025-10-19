package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface PartRepo {
    fun update(round: Domain): Flow<ResultDomain<Domain>>
    fun del(round: Domain): Flow<ResultDomain<Domain>>
}
