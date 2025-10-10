package com.count_out.data.models

import com.count_out.data.models.throwable.ResultData
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Speech

data class WeatherRequestDb (
    val latitude: Double,
    val longitude: Double,
    val timeZone: String
): Data {

    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: WeatherRequest {
        override val latitude: Double = this@WeatherRequestDb.latitude
        override val longitude: Double = this@WeatherRequestDb.longitude
        override val timeZone: String = this@WeatherRequestDb.timeZone
    }
}