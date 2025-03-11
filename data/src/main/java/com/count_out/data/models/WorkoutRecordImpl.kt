package com.count_out.data.models

import com.count_out.data.entity.WorkoutRecord
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.workout.Training

data class WorkoutRecordImpl(
    override var idWorkout: Long = 0L,
    override var trainingId: Long = 0,
    override var isSelected: Boolean = false,

    override var name: String = "",
    override var address: String = "",

    override var latitude: Double = 0.0,
    override var longitude: Double = 0.0,
    override var timeZone: String = "Europe/Moscow",
    override var timeStart: Long = 0,
    override var timeEnd: Long = 0,

    override var averagePace: Double = 0.0,
    override var maxPace: Double = 0.0,
    override var minPace: Double = 0.0,

    override var averageSpeed: Double = 0.0,
    override var maxSpeed: Double = 0.0,
    override var minSpeed: Double = 0.0,

    override var averageHeartRate: Double = 0.0,
    override var maxHeartRate: Double = 0.0,
    override var minHeartRate: Double = 0.0,

    override var resultSpeed: Double = 0.0,
    override var resultTime: Double = 0.0,
    override var resultWeight: Double = 0.0,
    override var resultAmount: Double = 0.0,
    override var resultRange: Double = 0.0,

    override var temperature: Double = 0.0,
    override var relativeHumidity2m: Int = 0,
    override var apparentTemperature: Double = 0.0,
    override var precipitation: Double = 0.0,
    override var rain: Double = 0.0,
    override var showers: Double = 0.0,
    override var snowfall: Double = 0.0,
    override var weatherCode: Int = 0,
    override var cloudCover: Int = 0,
    override var pressureMsl: Double = 0.0,
    override var surfacePressure: Double = 0.0,
    override var windSpeed10m: Double = 0.0,
    override var windDirection10m: Int = 0,
    override var windGusts10m: Double = 0.0,
    override val training: Training? = null,
): WorkoutRecord {
    override fun formTraining(training: Training) {  }
    override fun formWeather(weather: Weather) { }
}
