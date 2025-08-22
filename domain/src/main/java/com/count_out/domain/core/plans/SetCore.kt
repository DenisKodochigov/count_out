package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetCore @Inject constructor(private val repo: SetRepo): Core()  {
    fun copy(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.copy(set) }
    fun gets(exerciseId: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.gets(exerciseId) }
    fun get(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.get(set) }
    fun del(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.del(set) }
    fun update(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.update(set) }
}