package com.count_out.data.models

import com.count_out.data.models.throwable.ResultData
import com.count_out.domain.entity.workout.Domain

abstract class WeatherDb: Data {
    abstract val time: Long //= System.currentTimeMillis()
    abstract val interval: Int
    abstract val temperature2m: Double
    abstract val relativeHumidity2m: Int
    abstract val apparentTemperature: Double
    abstract val isDay: Int
    abstract val precipitation: Double
    abstract val rain: Double
    abstract val showers: Double
    abstract val snowfall: Double
    abstract val weatherCode: Int
    abstract val cloudCover: Int
    abstract val pressureMsl: Double
    abstract val surfacePressure: Double
    abstract val windSpeed10m: Double
    abstract val windDirection10m: Int
    abstract val windGusts10m: Double
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Domain{}
}