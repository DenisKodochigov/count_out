package com.count_out.domain.entity.location

import com.count_out.domain.entity.workout.Domain

interface Coordinate: Domain {
    val latitude: Double
    val longitude: Double
    val altitude: Double
    val accuracy: Float
    val speed: Float
    val distance: Float
    val timeLocation: Long
}