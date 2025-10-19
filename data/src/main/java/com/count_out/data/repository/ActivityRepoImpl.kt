package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.fromDomain
import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.source.room.ActivitySource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor( private val source: ActivitySource): ActivityRepo{
    override fun gets(): Flow<ResultDomain<Domain>> = source.gets().convertorFlow()
    override fun get(activity: Domain): Flow<ResultDomain<Domain>> =
        source.get(activity.fromDomain()).convertorFlow()

    override fun del(activity: Domain): Flow<ResultDomain<Domain>> =
        source.del(activity.fromDomain()).convertor()

    override fun copy(activity: Domain): Flow<ResultDomain<Domain>> =
        source.copy(activity.fromDomain()).convertor()

    override fun update(activity: Domain): Flow<ResultDomain<Domain>> =
        source.update(activity.fromDomain()).convertor()
}