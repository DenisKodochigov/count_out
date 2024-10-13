package com.example.count_out.data.openmeteo_api

import com.example.count_out.data.room.tables.WorkoutDB
import com.example.count_out.entity.weather.ResponseAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class DataSourceAPI @Inject constructor(private val openMeteo: OpenMeteoAPI) {

    suspend fun getWeather(workout: WorkoutDB): ResponseAPI {

        return CoroutineScope(Dispatchers.Default).async {
            openMeteo.getWeatherCurrent( workout.latitude,workout.longitude, workout.timeZone) }.await()
    }
}