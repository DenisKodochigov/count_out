package com.count_out.data.repository

import com.count_out.data.source.room.PlanSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.TrainingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingRepoImpl @Inject constructor(
    private val source: PlanSource): TrainingRepo, PrimeRepo() {

    override fun get(training: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.get(toTypeSource(training)).convertor()
    }
    override fun gets(): Flow<ResultUC<TypeRepo>> {
        return source.gets().convertor() }

    override fun del(training: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.del(toTypeSource(training)).nextActionOk { source.gets() }
    }
    override fun copy(training: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(training)).wrapFlow() }

    override fun update(training: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.update(toTypeSource(training)).wrapFlow() }
}
//override fun select(training: TypeRepo): Flow<ResultUC<TypeRepo>> {
//        return source.copy(convertorType(training)).concatOk { source.gets() }
//        source.update(convertorType(training))
//        return source.gets()
//    }