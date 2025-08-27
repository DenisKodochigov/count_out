package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface RingRepo {
    fun del(ring: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(ring: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(ring: TypeRepo): Flow<ResultUC<TypeRepo>>
}
//    fun get(ring: TypeRepo): Flow<ResultUC<TypeRepo>>
//    fun gets(trainingId: TypeRepo): Flow<ResultUC<TypeRepo>>