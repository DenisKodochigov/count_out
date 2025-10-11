package com.count_out.data

import com.count_out.data.models.entity.WeatherDb
import com.count_out.data.repository.WeatherRepoImpl
import com.count_out.data.source.network.WeatherSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock

class WeatherRepoImplTest {
    private val weatherSource = mock<WeatherSource>()
    private val weatherRepoImpl = WeatherRepoImpl(weatherSource)

    val target1WeatherDb = WeatherDb.EMPTY

    @ExperimentalCoroutinesApi
    @Test
    fun testWeather() = runTest {
//        val weather = target1WeatherDb
//        whenever(weatherSource.get(0.0,0.0, TimeZone.getDefault().displayName)).thenReturn(flowOf(weather as Weather))
//        val result = weatherRepoImpl.get(0.0,0.0, TimeZone.getDefault().displayName).last()
//        println ("$result")
//        assertEquals(weather, result)
        //verify(localUserDataSource).addUsers(weather)
    }

}
