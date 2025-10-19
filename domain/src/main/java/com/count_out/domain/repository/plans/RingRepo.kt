package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface RingRepo {
    fun update(ring: Domain): Flow<ResultDomain<Domain>>
    fun del(ring: Domain): Flow<ResultDomain<Domain>>
    fun insert(ring: Domain): Flow<ResultDomain<Domain>>
}
