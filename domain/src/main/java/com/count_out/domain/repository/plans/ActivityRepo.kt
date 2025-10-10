package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface ActivityRepo{
    fun gets(): Flow<ResultDomain<Domain>>
    fun get(activity: Domain): Flow<ResultDomain<Domain>>
    fun del(activity: Domain): Flow<ResultDomain<Domain>>
    fun copy(activity: Domain): Flow<ResultDomain<Domain>>
    fun update(activity: Domain): Flow<ResultDomain<Domain>>
}