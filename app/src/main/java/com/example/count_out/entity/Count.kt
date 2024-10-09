package com.example.count_out.entity

interface Count {
    val idCount: Long
    val workoutId: Long
    val coordinate: Coordinate?
    val coordinateId: Long
    val timeL: Long
    val heartRate: Int
    val geographicalHeight: Double
    val sensor1: Double
    val sensor2: Double
    val sensor3: Double
}