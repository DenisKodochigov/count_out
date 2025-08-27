package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityCore @Inject constructor(private val repo: ActivityRepo): Core() {
    fun gets(): Flow<ResultUC<TypeRepo>> {
        return repo.gets() }
    fun get(request: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.get(request) }
    fun copy(request: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.copy(request) }
    fun update(request: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.update(request) }
    fun del(request: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.del(request) }
}