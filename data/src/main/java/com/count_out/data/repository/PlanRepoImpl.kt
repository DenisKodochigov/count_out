package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.toData
import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.source.room.PlanSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.PlanRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlanRepoImpl @Inject constructor(
    private val source: PlanSource): PlanRepo {

    override fun get(idPlan: Domain): Flow<ResultDomain<Domain>> {
        return source.get(toData(idPlan)).convertorFlow()
    }
    override fun gets(): Flow<ResultDomain<Domain>> {
        return source.gets().convertorFlow() }

    override fun del(training: Domain): Flow<ResultDomain<Domain>> {
        return source.del(toData(training)).convertor()
    }
    override fun copy(training: Domain): Flow<ResultDomain<Domain>> {
        return source.copy(toData(training)).convertor() }

    override fun update(nameId: Domain): Flow<ResultDomain<Domain>> {
        return source.update(toData(nameId)).convertor() }
}
//override fun select(training: Domain): Flow<ResultUC<Domain>> {
//        return source.copy(convertorType(training)).concatOk { source.gets() }
//        source.update(convertorType(training))
//        return source.gets()
//    }