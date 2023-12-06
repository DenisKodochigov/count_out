package com.example.count_out.entity

interface Workout {
    val idWorkout: Long
    val name: String
    val listRound: List<Round>
    val open: Boolean
    val timeStart: Int
    val timeEnd: Int
    val temperature:Double
    val rainfall: Rainfall
    val counts: Counts
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
