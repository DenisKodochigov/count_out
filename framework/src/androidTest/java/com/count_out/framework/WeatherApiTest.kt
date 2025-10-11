package com.count_out.framework

import com.count_out.data.models.entity.WeatherDb
import com.count_out.framework.retrofit.weather.WeatherService
import com.count_out.framework.retrofit.weather.source.WeatherSourceImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class WeatherApiTest {
    companion object{
        private lateinit var weatherService: WeatherService
        private lateinit var source: WeatherSourceImpl
        private lateinit var expected: WeatherDb

        @BeforeAll
        @JvmStatic
        internal fun beforeAll(){
            val okHttpClient = OkHttpClient
                .Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/v1")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            weatherService = retrofit.create(WeatherService::class.java)
            source = WeatherSourceImpl(weatherService)
            expected = createExpectedWeather()
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {  }

        private fun createExpectedWeather() = WeatherDb.EMPTY
    }

    @Test @Order(1) fun weatherGetTest() = runTest{
//        val response = source.get(0.0, 0.0, "").first()
//        val response = expected
//        Assertions.assertEquals(expected, response, "Error test GETWEATHER")
    }
}