package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.TrainingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingCore @Inject constructor(private val repo: TrainingRepo): Core()  {
    fun get(training: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.get(training) }
    fun gets(): Flow<ResultUC<TypeRepo>>{
        return repo.gets() }
    fun del(training: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.del(training) }
    fun copy(training: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.copy(training) }
    fun update(training: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.update(training) }

}