package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.weather.Weather
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
//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = Domain.EMPTY

    companion object {
        fun fromDomain(domain: Domain): WeatherDb {
            return when (domain) {
                is Weather -> object: WeatherDb(){
                    override val time: Long = (domain as Weather).time
                    override val interval: Int = (domain as Weather).interval
                    override val temperature2m: Double = (domain as Weather).temperature2m
                    override val relativeHumidity2m: Int = (domain as Weather).relativeHumidity2m
                    override val apparentTemperature: Double = (domain as Weather).apparentTemperature
                    override val isDay: Int = (domain as Weather).isDay
                    override val precipitation: Double = (domain as Weather).precipitation
                    override val rain: Double = (domain as Weather).rain
                    override val showers: Double = (domain as Weather).showers
                    override val snowfall: Double = (domain as Weather).snowfall
                    override val weatherCode: Int = (domain as Weather).weatherCode
                    override val cloudCover: Int = (domain as Weather).cloudCover
                    override val pressureMsl: Double = (domain as Weather).pressureMsl
                    override val surfacePressure: Double = (domain as Weather).surfacePressure
                    override val windSpeed10m: Double = (domain as Weather).windSpeed10m
                    override val windDirection10m: Int = (domain as Weather).windDirection10m
                    override val windGusts10m: Double = (domain as Weather).windGusts10m
                }
                else -> throw IllegalArgumentException("Unsupported domain type")
            }
        }
        val EMPTY  = object: WeatherDb(){
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