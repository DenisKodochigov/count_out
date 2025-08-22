package com.count_out.data.repository

import com.count_out.data.source.local.LastPlanSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.entity.TypeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LastPlanRepoImpl @Inject constructor(
    private val sourceTraining: TrainingSource,
    private val source: LastPlanSource): LastPlanRepo, PrimeRepo()
{
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLastUsedPlan(): Flow<ResultUC<TypeRepo>> =
        source.getLastPlan().concat1{sourceTraining.get(it)}

    override fun saveLastUsedPlan(id: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.saveLastPlan(toTypeSource( id)).convertor()

}