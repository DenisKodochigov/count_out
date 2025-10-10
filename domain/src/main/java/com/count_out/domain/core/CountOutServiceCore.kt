package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.CountOutServiceRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceCore @Inject constructor(private val repo: CountOutServiceRepo): Core() {
    fun bind(): Flow<ResultDomain<Domain>>{
        return repo.bind() }
    fun unbind(): Flow<ResultDomain<Domain>>{
        return repo.unbind() }
}