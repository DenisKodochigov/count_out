package com.count_out.data.source.network

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
    fun get(weatherRequest: Data): Flow<ResultData<Data>>
}