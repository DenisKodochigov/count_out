package com.count_out.data.entity

import com.count_out.entity.entity.workout.Training
import com.count_out.entity.entity.weather.Weather

interface WorkoutRecord {
    val idWorkout: Long
    val trainingId: Long
    val training: Training?
    val isSelected: Boolean
    val name: String
    val address: String

    val latitude: Double
    val longitude: Double

    val temperature:Double
    val relativeHumidity2m: Int
    val apparentTemperature: Double
    val precipitation: Double
    val rain: Double
    val showers: Double
    val snowfall: Double
    val weatherCode: Int
    val cloudCover: Int
    val pressureMsl: Double
    val surfacePressure: Double
    val windSpeed10m: Double
    val windDirection10m: Int
    val windGusts10m: Double

    val timeZone: String
    val timeStart: Long
    val timeEnd: Long

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

    fun formTraining(training: Training)
    fun formWeather(weather: Weather)
}