package com.count_out.framework.retrofit.weather.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.network.WeatherSource
import com.count_out.framework.retrofit.weather.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherSource {
    override fun get(weatherRequest: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(
            if (weatherRequest is TypeSource.WeatherRequestT){
                ResultSource.Success(TypeSource.WeatherT(
                    weatherService.getWeather(
                        weatherRequest.item.latitude,
                        weatherRequest.item.longitude,
                        weatherRequest.item.timeZone).current.toWeatherSource()))

                    } else ResultSource.Error(ThrowableDS.NotValidType())
        ) }
    }
}