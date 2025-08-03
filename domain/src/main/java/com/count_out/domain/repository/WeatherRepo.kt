package com.count_out.domain.repository

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.weather.WeatherRequest
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun get(weatherRequest: TypeRepo): Flow<ResultUC<TypeRepo>>
}