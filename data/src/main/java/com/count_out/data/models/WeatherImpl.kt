package com.count_out.data.models

import com.count_out.domain.entity.weather.Weather

data class WeatherImpl(
    override val time: Long = System.currentTimeMillis(),
    override val interval: Int,
    override val temperature2m: Double,
    override val relativeHumidity2m: Int,
    override val apparentTemperature: Double,
    override val isDay: Int ,
    override val precipitation: Double,
    override val rain: Double,
    override val showers: Double,
    override val snowfall: Double,
    override val weatherCode: Int,
    override val cloudCover: Int,
    override val pressureMsl: Double,
    override val surfacePressure: Double,
    override val windSpeed10m: Double,
    override val windDirection10m: Int,
    override val windGusts10m: Double,
) : Weather