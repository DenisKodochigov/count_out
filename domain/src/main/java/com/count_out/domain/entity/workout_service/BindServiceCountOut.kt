package com.count_out.domain.entity.workout_service

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface BindServiceCountOut {
    fun bindService(): Flow<ResultDomain<Domain>>
    fun unbindService(): Flow<ResultDomain<Domain>>
}