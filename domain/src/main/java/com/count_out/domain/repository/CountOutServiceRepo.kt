package com.count_out.domain.repository

import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface CountOutServiceRepo {
    fun bind(): Flow<ResultUC<TypeRepo>>
    fun unbind(): Flow<ResultUC<TypeRepo>>
}