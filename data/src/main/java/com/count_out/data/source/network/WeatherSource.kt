package com.count_out.data.source.network

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
    fun get(weatherRequest: Data): Flow<ResultData<Data>>
}