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
        return wrapFlow(source.copy(toTypeSource(set)))
    }

    override fun del(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return wrapFlow(source.del(toTypeSource(set)))
    }
    override fun update(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return wrapFlow(source.update(toTypeSource(set)))
    }
}