package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepo{
    fun gets(): Flow<ResultUC<List<Activity>>>
    fun get(id: Long): Flow<ResultUC<Activity>>
    fun del(activity: Activity): Flow<ResultUC<Int>>
    fun copy(activity: Activity): Flow<ResultUC<Activity>>
    fun update(activity: Activity): Flow<ResultUC<Activity>>
}