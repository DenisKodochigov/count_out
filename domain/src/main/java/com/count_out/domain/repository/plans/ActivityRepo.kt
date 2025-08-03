package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface ActivityRepo{
    fun gets(): Flow<ResultUC<TypeRepo>>
    fun get(id: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun del(activity: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(activity: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(activity: TypeRepo): Flow<ResultUC<TypeRepo>>
}