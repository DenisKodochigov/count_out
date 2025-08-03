package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface TrainingRepo {
    fun get(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<Training>
    fun gets(): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
    fun del(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
    fun copy(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
//    fun select(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
    fun update(training: TypeRepo): Flow<ResultUC<TypeRepo>> // Flow<Training>
    fun updates(training: TypeRepo): Flow<ResultUC<TypeRepo>> //: Flow<List<Training>>
}