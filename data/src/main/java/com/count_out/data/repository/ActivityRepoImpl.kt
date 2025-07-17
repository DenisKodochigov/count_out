package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.ActivityImpl
import com.count_out.data.source.room.ActivitySource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.repository.plans.ActivityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivityRepoImpl @Inject constructor(
    private val converter: ConverterResult,
    private val source: ActivitySource
): ActivityRepo, PrimeRepo() {
    override fun gets(): Flow<ResultUC<List<Activity>>> = source.gets().map{ converter.execute(it) }
    override fun get(id: Long): Flow<ResultUC<Activity>> = source.get(id).map{ converter.execute(it) }
    override fun del(activity: Activity): Flow<ResultUC<Int>> {
        return source.del(activity.idActivity).resultUC()
    }
    override fun copy(activity: Activity): Flow<ResultUC<Activity>> {
        return source.copy(ActivityImpl(activity)).concat { source.get(it) }
    }
    override fun update(activity: Activity): Flow<ResultUC<Activity>> {
        return get(activity.idActivity)
    }

    override fun converter(): ConverterResult =converter
}