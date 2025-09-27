package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface PlanRepo {
    fun get(plan: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<Training>
    fun gets(): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
    fun del(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
    fun copy(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
//    fun select(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
    fun update(nameId: TypeRepo): Flow<ResultUC<TypeRepo>> // Flow<Training>
}