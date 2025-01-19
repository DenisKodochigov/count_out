package com.example.data.entity

import com.example.domain.entity.weather.Weather

data class WeatherImpl(
    override val time: String = System.currentTimeMillis().toString(),
    override val interval: Int? = null,
    override val temperature2m: Double? = null,
    override val relativeHumidity2m: Int? = null,
    override val apparentTemperature: Double? = null,
    override val isDay: Int ? = null,
    override val precipitation: Double? = null,
    override val rain: Double? = null,
    override val showers: Double? = null,
    override val snowfall: Double? = null,
    override val weatherCode: Int? = null,
    override val cloudCover: Int? = null,
    override val pressureMsl: Double? = null,
    override val surfacePressure: Double? = null,
    override val windSpeed10m: Double? = null,
    override val windDirection10m: Int? = null,
    override val windGusts10m: Double? = null,
) : Weather