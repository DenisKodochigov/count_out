package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun copy(set: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun del(set: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(set: TypeRepo): Flow<ResultUC<TypeRepo>>
}