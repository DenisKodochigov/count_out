package com.example.data

import com.example.data.entity.WeatherImpl
import com.example.data.repository.TrainingRepoImpl
import com.example.data.repository.WeatherRepoImpl
import com.example.data.source.network.WeatherSource
import com.example.data.source.room.TrainingSource
import com.example.domain.entity.weather.Weather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class WeatherRepoImplTest {
    private val weatherSource = mock<WeatherSource>()
    private val trainingSource = mock<TrainingSource>()
    private val trainingRepoImpl = TrainingRepoImpl(trainingSource)
    private val weatherRepoImpl = WeatherRepoImpl(weatherSource)


    @ExperimentalCoroutinesApi
    @Test
    fun testGetUsers() = runTest {
        val weather = listOf(WeatherImpl())
        whenever(weatherSource.get()).thenReturn(flowOf(weather as Weather))
        val result = weatherRepoImpl.get()
//        assertEquals(weather, result)
//        verify(localUserDataSource).addUsers(weather)
    }
}