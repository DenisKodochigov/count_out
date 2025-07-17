package com.count_out.data.source.room

import com.count_out.data.models.ActivityImpl
import com.count_out.data.models.throwable.ResultSource
import kotlinx.coroutines.flow.Flow

interface ActivitySource {
    fun gets(): Flow<ResultSource<List<ActivityImpl>>>
    fun get(id: Long): Flow<ResultSource<ActivityImpl>>
    fun copy(activity: ActivityImpl): Flow<ResultSource<Long>>
    fun update(activity: ActivityImpl): Flow<ResultSource<ActivityImpl>>
    fun del(id: Long): Flow<ResultSource<Int>>
    fun delWithCheck(id: Long)
}