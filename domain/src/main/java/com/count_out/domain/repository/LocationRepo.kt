package com.count_out.domain.repository

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultDomain
import kotlinx.coroutines.flow.Flow

interface LocationRepo {
    fun getLocation(): Flow<ResultDomain<TypeRepo>>
}