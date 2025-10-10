package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import kotlinx.coroutines.flow.Flow

interface ActivitySource {
    fun gets(): Flow<ResultData<Data>>
    fun get(activity: Data): Flow<ResultData<Data>>
    fun copy(activity: Data): ResultData<Data>
    fun update(activity: Data): ResultData<Data>
    fun del(idActivity: Data): ResultData<Data>
}
