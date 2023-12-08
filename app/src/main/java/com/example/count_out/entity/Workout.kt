package com.example.count_out.entity

interface Workout {
    val idWorkout: Long
    val name: String
    val open: Boolean
    val counts: Counts
    val rounds: List<Round>

    val temperature:Double
    val rainfall: Rainfall

    val timeStart: Int
    val timeEnd: Int

    val averagePace: Double
    val maxPace: Double
    val minPace: Double

    val averageSpeed: Double
    val maxSpeed: Double
    val minSpeed: Double

    val averageHeartRate: Double
    val maxHeartRate: Double
    val minHeartRate: Double

    val resultSpeed :Double
    val resultTime :Double
    val resultWeight :Double
    val resultAmount :Double
    val resultRange  :Double
}
