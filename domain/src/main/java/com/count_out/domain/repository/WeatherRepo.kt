package com.count_out.domain.repository

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun get(weatherRequest: TypeRepo): Flow<ResultUC<TypeRepo>>
}