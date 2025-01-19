package com.example.data.repository

import com.example.data.source.network.WeatherSource
import com.example.domain.entity.weather.Weather
import com.example.domain.repository.WeatherRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor( private val weatherSource: WeatherSource): WeatherRepo {
    override fun get(): Flow<Weather> = weatherSource.get()
}