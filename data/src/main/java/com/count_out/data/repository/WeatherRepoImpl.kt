package com.count_out.data.repository

import com.count_out.data.source.network.WeatherSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val source: WeatherSource): WeatherRepo, PrimeRepo() {
    override fun get(weatherRequest: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.get(toTypeSource(weatherRequest)).convertor()
    }
}