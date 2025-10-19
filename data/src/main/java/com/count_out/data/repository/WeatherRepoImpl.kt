package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.models.entity.WeatherRequestDb
import com.count_out.data.source.network.WeatherSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor( private val source: WeatherSource): WeatherRepo{
    override fun get(weatherRequest: Domain): Flow<ResultDomain<Domain>> {
        return source.get(WeatherRequestDb.fromDomain(weatherRequest)).convertorFlow()
    }
}