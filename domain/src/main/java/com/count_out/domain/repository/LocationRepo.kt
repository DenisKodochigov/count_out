package com.count_out.domain.repository

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface LocationRepo {
    fun getLocation(): Flow<ResultUC<TypeRepo>>
}