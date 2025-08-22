package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import kotlinx.coroutines.flow.Flow

interface RoundRepo {
    fun get(round: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun gets(trainingId: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(round: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun del(round: TypeRepo): Flow<ResultUC<TypeRepo>>
}