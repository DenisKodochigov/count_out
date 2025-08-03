package com.count_out.data.source.network

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface  WeatherSource {
//    fun get(latitude: TypeSource, longitude: TypeSource, timezone: TypeSource): Flow<ResultSource<TypeSource>>
    fun get(weatherRequest: TypeSource): Flow<ResultSource<TypeSource>>
}