package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.toData
import com.count_out.data.source.room.ActivitySource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor( private val source: ActivitySource): ActivityRepo, PrimeRepo() {
    override fun gets(): Flow<ResultDomain<Domain>> = source.gets().convertorFlow()
    override fun get(activity: Domain): Flow<ResultDomain<Domain>> =
        source.get(toData(activity)).convertorFlow()

    override fun del(activity: Domain): Flow<ResultDomain<Domain>> =
        source.del(toData(activity)).convertor()

    override fun copy(activity: Domain): Flow<ResultDomain<Domain>> =
        source.copy(toData(activity)).convertor()

    override fun update(activity: Domain): Flow<ResultDomain<Domain>> =
        source.update(toData(activity)).convertor()
}