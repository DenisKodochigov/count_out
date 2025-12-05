package com.count_out.domain.entity.workout_service

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface ServiceWorkOut {
    fun onStart(forWork: Domain): Flow<ResultDomain<Domain>>
    fun onStop(): Flow<ResultDomain<Domain>>
}