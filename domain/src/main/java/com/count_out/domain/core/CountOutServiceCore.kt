package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout_service.BindServiceCountOut
import com.count_out.domain.entity.workout_service.ServiceWorkOut
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceCore @Inject constructor(private val service: ServiceWorkOut,
                                              private val bind: BindServiceCountOut
): Core() {

    fun start(forWork: Domain): Flow<ResultDomain<Domain>>{
        return service.onStart(forWork) }
    fun stop(): Flow<ResultDomain<Domain>>{
        return service.onStop() }
}