package com.example.count_out.entity.no_use

import com.example.count_out.entity.Rainfall
import com.example.count_out.entity.Training

interface Workout {
    val idWorkout: Long
    val name: String
    val trainingId: Long
    val training: Training?
    val isSelected: Boolean

    val temperature:Double
    val rainfallId: Int
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
