package com.count_out.data.models

data class WorkoutRecordImpl(
    var idWorkout: Long = 0L,
    var trainingId: Long = 0,
    var isSelected: Boolean = false,

    var name: String = "",
    var address: String = "",

    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var timeZone: String = "Europe/Moscow",
    var timeStart: Long = 0,
    var timeEnd: Long = 0,

    var averagePace: Double = 0.0,
    var maxPace: Double = 0.0,
    var minPace: Double = 0.0,

    var averageSpeed: Double = 0.0,
    var maxSpeed: Double = 0.0,
    var minSpeed: Double = 0.0,

    var averageHeartRate: Double = 0.0,
    var maxHeartRate: Double = 0.0,
    var minHeartRate: Double = 0.0,

    var resultSpeed: Double = 0.0,
    var resultTime: Double = 0.0,
    var resultWeight: Double = 0.0,
    var resultAmount: Double = 0.0,
    var resultRange: Double = 0.0,

    var temperature: Double = 0.0,
    var relativeHumidity2m: Int = 0,
    var apparentTemperature: Double = 0.0,
    var precipitation: Double = 0.0,
    var rain: Double = 0.0,
    var showers: Double = 0.0,
    var snowfall: Double = 0.0,
    var weatherCode: Int = 0,
    var cloudCover: Int = 0,
    var pressureMsl: Double = 0.0,
    var surfacePressure: Double = 0.0,
    var windSpeed10m: Double = 0.0,
    var windDirection10m: Int = 0,
    var windGusts10m: Double = 0.0,
    val training: PlanDb? = null,
)