package com.count_out.domain.repository

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface LocationRepo {
    fun getLocation(): Flow<ResultDomain<Domain>>
}