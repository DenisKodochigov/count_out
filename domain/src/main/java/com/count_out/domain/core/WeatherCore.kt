package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherCore @Inject constructor(private val repo: WeatherRepo): Core()  {
    fun get(weatherRequest: Domain): Flow<ResultDomain<Domain>>{
        return repo.get(weatherRequest) }
}