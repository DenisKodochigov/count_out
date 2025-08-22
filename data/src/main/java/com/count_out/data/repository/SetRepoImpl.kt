package com.count_out.data.repository

import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(
    private val source: SetSource): SetRepo, PrimeRepo() {

    override fun gets(exerciseId: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.gets(toTypeSource( exerciseId)).convertor()

    override fun get(set: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.get(toTypeSource(set)).convertor()

    override fun copy(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(set))
            .nextAction{ source.gets(it)}
    }

    override fun del(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(set)
        return source.del(typeSource).nextActionOk { source.get(typeSource) }
    }
    override fun update(set: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val typeSource = toTypeSource(set)
        return source.update(typeSource).nextActionOk { source.get(typeSource) }
    }
}