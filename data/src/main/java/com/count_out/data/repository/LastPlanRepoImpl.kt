package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.toData
import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.models.ResultData.Companion.flatMapFlow
import com.count_out.data.source.local.LastPlanSource
import com.count_out.data.source.room.PlanSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.LastPlanRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class LastPlanRepoImpl @Inject constructor(
    private val sourceTraining: PlanSource,
    private val source: LastPlanSource): LastPlanRepo
{
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLastUsedPlan(): Flow<ResultDomain<Domain>> =
        source.getLastPlan().flatMapConcat { resultDS ->
            resultDS.flatMapFlow { res-> sourceTraining.get(res) }.convertorFlow() }

    override fun saveLastUsedPlan(id: Domain): Flow<ResultDomain<Domain>> =
        source.saveLastPlan(toData( id)).convertorFlow()

}