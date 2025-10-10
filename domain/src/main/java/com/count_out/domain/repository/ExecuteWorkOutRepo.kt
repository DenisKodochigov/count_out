package com.count_out.domain.repository

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface ExecuteWorkOutRepo {
    fun start(): Flow<ResultDomain<Domain>>
    fun stop(): Flow<ResultDomain<Domain>>
    fun pause(): Flow<ResultDomain<Domain>>
    fun save(): Flow<ResultDomain<Domain>>
    fun upInterval(): Flow<ResultDomain<Domain>>
    fun downInterval(): Flow<ResultDomain<Domain>>
    fun getPlan(): Flow<ResultDomain<Domain>>
}