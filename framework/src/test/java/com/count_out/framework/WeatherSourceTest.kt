package com.count_out.framework

import com.count_out.data.models.WeatherImpl
import com.count_out.framework.retrofit.weather.WeatherService
import com.count_out.framework.retrofit.weather.entity.ResponseOpenMeteo
import com.count_out.framework.retrofit.weather.entity.WeatherResponse
import com.count_out.framework.retrofit.weather.entity.WeatherUnitsOpenMeteo
import com.count_out.framework.retrofit.weather.source.WeatherSourceImpl
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class WeatherSourceTest {

    private val api = mock<WeatherService>()
    private val source = WeatherSourceImpl(api)

    @Test
    fun testGetWeather()= runTest{
        val expectedWeather = getWeather()
        whenever(api.getWeather(0.0,0.0,"")).thenReturn(getResponseOpenMeteo())
//        val result = source.get(0.0,0.0,"").first()
//        Assertions.assertEquals(expectedWeather, result)
    }

    private fun getWeather() = WeatherImpl(
        time = 0,
        interval = 0,
        temperature2m = 0.0,
        relativeHumidity2m = 0,
        apparentTemperature = 0.0,
        isDay = 0,
        precipitation = 0.0,
        rain = 0.0,
        showers = 0.0,
        snowfall = 0.0,
        weatherCode = 0,
        cloudCover = 0,
        pressureMsl = 0.0,
        surfacePressure = 0.0,
        windSpeed10m = 0.0,
        windDirection10m = 0,
        windGusts10m = 0.0,
    )
    private fun getResponseOpenMeteo() = ResponseOpenMeteo(
        latitude = 0.0,
        longitude = 0.0,
        generationTimeMS = 0.0,
        utcOffsetSeconds = 0,
        timezone = "",
        timezoneAbbreviation = "",
        elevation = 0.0,
        currentUnits = WeatherUnitsOpenMeteo(),
        current = WeatherResponse()
    )
}