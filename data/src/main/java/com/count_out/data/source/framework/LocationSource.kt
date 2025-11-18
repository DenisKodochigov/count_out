package com.count_out.data.source.framework

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface LocationSource {
    fun getLocation(): Flow<ResultData<Data>>
    fun stopService(): Flow<ResultData<Data>>
}