package com.count_out.data.repository

import com.count_out.data.models.throwable.ResultSource.Companion.flatMapFlow
import com.count_out.data.source.local.LastPlanSource
import com.count_out.data.source.room.PlanSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.LastPlanRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class LastPlanRepoImpl @Inject constructor(
    private val sourceTraining: PlanSource,
    private val source: LastPlanSource): LastPlanRepo, PrimeRepo()
{
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLastUsedPlan(): Flow<ResultUC<TypeRepo>> =
        source.getLastPlan().flatMapConcat { resultDS ->
            resultDS.flatMapFlow { res-> sourceTraining.get(res) }.convertor() }

    override fun saveLastUsedPlan(id: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.saveLastPlan(toTypeSource( id)).convertor()

}