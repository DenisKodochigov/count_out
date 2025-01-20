package com.example.data

import com.example.data.entity.WeatherImpl
import com.example.data.repository.TrainingRepoImpl
import com.example.data.repository.WeatherRepoImpl
import com.example.data.source.network.WeatherSource
import com.example.data.source.room.RoundSource
import com.example.data.source.room.TrainingSource
import com.example.domain.entity.weather.Weather
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.TimeZone

class WeatherRepoImplTest {
    private val weatherSource = mock<WeatherSource>()
    private val trainingSource = mock<TrainingSource>()
    private val roundSource = mock<RoundSource>()
    private val trainingRepoImpl = TrainingRepoImpl(trainingSource, roundSource)
    private val weatherRepoImpl = WeatherRepoImpl(weatherSource)

//    private val LOG: Logger = Logger.getLogger(this.javaClass.name)

//    companion object {
//        val LOG: Logger = Logger.getLogger(WeatherRepoImplTest::class.java.name)
//    }
    val target1Weather = WeatherImpl(time=0, interval = 0, temperature2m = 0.0, relativeHumidity2m = 0,
        apparentTemperature = 0.0, isDay = 0, precipitation = 0.0, rain = 0.0, showers = 0.0,
        snowfall = 0.0, weatherCode = 0, cloudCover = 0, pressureMsl = 0.0, surfacePressure = 0.0,
        windSpeed10m = 0.0, windDirection10m = 0, windGusts10m = 0.0,)
    val target2Weather = WeatherImpl(time=1737396808979, interval=0, temperature2m=0.0,
        relativeHumidity2m=0, apparentTemperature=0.0, isDay=0, precipitation=0.0, rain=0.0,
        showers=0.0, snowfall=0.0, weatherCode=0, cloudCover=0, pressureMsl=0.0, surfacePressure=0.0,
        windSpeed10m=0.0, windDirection10m=0, windGusts10m=0.0)


    @ExperimentalCoroutinesApi
    @Test
    fun testWeather() = runTest {
        val weather = target1Weather
        whenever(weatherSource.get(0.0,0.0, TimeZone.getDefault().displayName)).thenReturn(flowOf(weather as Weather))
        val result = weatherRepoImpl.get(0.0,0.0, TimeZone.getDefault().displayName).last()
        println ("$result")
        assertEquals(weather, result)
        //verify(localUserDataSource).addUsers(weather)
    }
}
