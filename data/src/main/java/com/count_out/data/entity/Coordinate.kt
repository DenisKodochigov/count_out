package com.count_out.data.entity

interface Coordinate {
    val latitude: Double
    val longitude: Double
    val altitude: Double
    val accuracy: Float
    val speed: Float
    val distance: Float
    val timeLocation: Long
}