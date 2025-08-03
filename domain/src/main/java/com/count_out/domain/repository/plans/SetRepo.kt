package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun copy(set: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun gets(exerciseId: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun get(set: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun del(set: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(set: TypeRepo): Flow<ResultUC<TypeRepo>>
}