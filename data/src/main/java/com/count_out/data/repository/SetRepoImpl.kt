package com.count_out.data.repository

import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(
    private val source: SetSource): SetRepo, PrimeRepo() {

    override fun copy(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(set)).wrapFlow()
    }

    override fun del(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.del(toTypeSource(set)).wrapFlow()
    }
    override fun update(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.update(toTypeSource(set)).wrapFlow()
    }
}