package com.count_out.domain.repository

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface ExecuteWorkOutRepo {
    fun start(): Flow<ResultUC<TypeRepo>>
    fun stop(): Flow<ResultUC<TypeRepo>>
    fun pause(): Flow<ResultUC<TypeRepo>>
    fun save(): Flow<ResultUC<TypeRepo>>
    fun upInterval(): Flow<ResultUC<TypeRepo>>
    fun downInterval(): Flow<ResultUC<TypeRepo>>
    fun getPlan(): Flow<ResultUC<TypeRepo>>
    fun getPlan1(): Flow<ResultUC<Domain>>
}