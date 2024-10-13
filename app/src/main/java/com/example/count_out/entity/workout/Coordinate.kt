package com.example.count_out.entity.workout

interface Coordinate {
    val latitude: Double
    val longitude: Double
    val altitude: Double
    val accuracy: Float
    val speed: Float
    val distance: Float
    val timeLocation: Long
}