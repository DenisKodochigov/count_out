package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.location.Coordinate

interface CoordinateDb: Data {
    val latitude: Double
    val longitude: Double
    val altitude: Double
    val timeLocation: Long
    val accuracy: Float
    val distance: Float
    val speed: Float
    override fun toDomain(ind: Int) = object: Coordinate {
        override val latitude: Double = this@CoordinateDb.latitude
        override val longitude: Double = this@CoordinateDb.longitude
        override val altitude: Double = this@CoordinateDb.altitude
        override val timeLocation: Long = this@CoordinateDb.timeLocation
        override val accuracy: Float = this@CoordinateDb.accuracy
        override val speed: Float = this@CoordinateDb.speed
        override val distance: Float = this@CoordinateDb.distance
    }
    companion object{
        fun new(
            latitude: Double = 0.0,
            longitude: Double = 0.0,
            altitude: Double = 0.0,
            timeLocation: Long = 0,
            accuracy: Float = 0f,
            distance: Float = 0f,
            speed: Float = 0f,
        ) = object: CoordinateDb{
            override val latitude: Double = latitude
            override val longitude: Double = longitude
            override val altitude: Double = altitude
            override val timeLocation: Long = timeLocation
            override val accuracy: Float = accuracy
            override val distance: Float = distance
            override val speed: Float = speed
        }
        val EMPTY =  object: CoordinateDb{
            override val latitude: Double = 0.0
            override val longitude: Double = 0.0
            override val altitude: Double = 0.0
            override val timeLocation: Long = 0
            override val accuracy: Float = 0f
            override val distance: Float = 0f
            override val speed: Float = 0f
        }
    }
}