package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.entity.workout.Domain

data class WeatherRequestDb (
    val latitude: Double,
    val longitude: Double,
    val timeZone: String
): Data {

//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: WeatherRequest {
        override val latitude: Double = this@WeatherRequestDb.latitude
        override val longitude: Double = this@WeatherRequestDb.longitude
        override val timeZone: String = this@WeatherRequestDb.timeZone
    }
    companion object {
        fun fromDomain(domain: Domain): WeatherRequestDb {
            return when (domain) {
                is WeatherRequest -> WeatherRequestDb(
                    latitude = domain.latitude,
                    longitude = domain.longitude,
                    timeZone = domain.timeZone
                )
                else -> throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
}