package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.workout.Domain

interface WeatherDb: Data {
    val time: Long //= System.currentTimeMillis()
    val interval: Int
    val temperature2m: Double
    val relativeHumidity2m: Int
    val apparentTemperature: Double
    val isDay: Int
    val precipitation: Double
    val rain: Double
    val showers: Double
    val snowfall: Double
    val weatherCode: Int
    val cloudCover: Int
    val pressureMsl: Double
    val surfacePressure: Double
    val windSpeed10m: Double
    val windDirection10m: Int
    val windGusts10m: Double
//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = Domain.EMPTY

    companion object {
        fun fromDomain(domain: Domain): WeatherDb {
            return when (domain) {
                is Weather -> object: WeatherDb{
                    override val time: Long = domain.time
                    override val interval: Int = domain.interval
                    override val temperature2m: Double = domain.temperature2m
                    override val relativeHumidity2m: Int = domain.relativeHumidity2m
                    override val apparentTemperature: Double = domain.apparentTemperature
                    override val isDay: Int = domain.isDay
                    override val precipitation: Double = domain.precipitation
                    override val rain: Double = domain.rain
                    override val showers: Double = domain.showers
                    override val snowfall: Double = domain.snowfall
                    override val weatherCode: Int = domain.weatherCode
                    override val cloudCover: Int = domain.cloudCover
                    override val pressureMsl: Double = domain.pressureMsl
                    override val surfacePressure: Double = domain.surfacePressure
                    override val windSpeed10m: Double = domain.windSpeed10m
                    override val windDirection10m: Int = domain.windDirection10m
                    override val windGusts10m: Double = domain.windGusts10m
                }
                else -> EMPTY
            }
        }
        val EMPTY = object: WeatherDb{
            override val time: Long = 0
            override val interval: Int = 0
            override val temperature2m: Double = 0.0
            override val relativeHumidity2m: Int = 0
            override val apparentTemperature: Double = 0.0
            override val isDay: Int = 0
            override val precipitation: Double = 0.0
            override val rain: Double = 0.0
            override val showers: Double = 0.0
            override val snowfall: Double = 0.0
            override val weatherCode: Int = 0
            override val cloudCover: Int = 0
            override val pressureMsl: Double = 0.0
            override val surfacePressure: Double = 0.0
            override val windSpeed10m: Double = 0.0
            override val windDirection10m: Int = 0
            override val windGusts10m: Double = 0.0
        }
    }
}