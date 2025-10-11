package com.count_out.framework.retrofit.weather.source

import com.count_out.data.models.Data
import com.count_out.data.models.entity.WeatherRequestDb
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.network.WeatherSource
import com.count_out.framework.retrofit.weather.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherSourceImpl @Inject constructor( private val weatherService: WeatherService): WeatherSource {
    override fun get(weatherRequest: Data): Flow<ResultData<Data>> {

        return flow { emit(
                if (weatherRequest is WeatherRequestDb) {
                    ResultData.Success(
                        weatherService.getWeather(
                            weatherRequest.latitude,
                            weatherRequest.longitude,
                            weatherRequest.timeZone).current.toWeatherSource()
                    )
                } else ResultData.Error(ThrowableDS.NotValidType())
            )
        }
    }
}