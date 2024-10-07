package com.example.count_out.entity

interface Coordinate {
    val latitude: Double
    val longitude: Double
    val altitude: Double
    val accuracy: Float
    val speed: Float
    val time: Long
}