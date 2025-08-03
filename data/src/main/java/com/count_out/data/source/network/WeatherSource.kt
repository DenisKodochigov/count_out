package com.count_out.data.source.network

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
//    fun get(latitude: TypeSource, longitude: TypeSource, timezone: TypeSource): Flow<ResultSource<TypeSource>>
    fun get(weatherRequest: TypeSource): Flow<ResultSource<TypeSource>>
}