package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun copy(set: Domain): Flow<ResultDomain<Domain>>
    fun del(set: Domain): Flow<ResultDomain<Domain>>
    fun update(set: Domain): Flow<ResultDomain<Domain>>
}