package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.domain.entity.weather.Weather
import com.example.count_out.entity.workout.Training
import com.example.count_out.entity.workout.Workout

@Entity(tableName = "tb_workout")
data class WorkoutDB(
    @PrimaryKey(autoGenerate = true) override var idWorkout: Long = 0L,
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
    @Ignore override val training: Training? = null,
) : Workout {

    override fun formTraining(training: Training){
        this.trainingId = training.idTraining
    }

    override fun formWeather(weather: Weather) {
        temperature = weather.temperature2m
        relativeHumidity2m = weather.relativeHumidity2m
        apparentTemperature = weather.apparentTemperature
        precipitation = weather.precipitation
        rain = weather.rain
        showers = weather.showers
        snowfall = weather.snowfall
        weatherCode = weather.weatherCode
        cloudCover = weather.cloudCover
        pressureMsl = weather.pressureMsl
        surfacePressure = weather.surfacePressure
        windSpeed10m = weather.windSpeed10m
        windDirection10m = weather.windDirection10m
        windGusts10m = weather.windGusts10m
    }
//    constructor(): this( minPace = 0.0)

//    constructor(
//        idWorkout: Long,
//        name: String,
//        open: Boolean,
//        timeStart: Int,
//        timeEnd: Int,
//        temperature: Double,
//        rainfall: Rainfall,
//        averagePace: Double,
//        maxPace: Double,
//        minPace: Double,
//        averageSpeed: Double,
//        maxSpeed: Double,
//        minSpeed: Double,
//        averageHeartRate: Double,
//        maxHeartRate: Double,
//        minHeartRate: Double,
//        resultSpeed: Double,
//        resultTime: Double,
//        resultWeight: Double,
//        resultAmount: Double,
//        resultRange: Double,
//    ): this (
//        idWorkout = 0L,
//        name = "",
//        open = false,
//        timeStart = 0,
//        timeEnd = 0,
//        temperature = 0.0,
//        rainfall = Rainfall.MEDIUM,
//        averagePace = 0.0,
//        maxPace = 0.0,
//        minPace = 0.0,
//        averageSpeed = 0.0,
//        maxSpeed = 0.0,
//        minSpeed = 0.0,
//        averageHeartRate = 0.0,
//        maxHeartRate = 0.0,
//        minHeartRate = 0.0,
//        resultSpeed = 0.0,
//        resultTime = 0.0,
//        resultWeight = 0.0,
//        resultAmount = 0.0,
//        resultRange = 0.0,
//    ){
//        this.idWorkout = idWorkout
//        this.name = name
//        this.open = open
//        this.timeStart = timeStart
//        this.timeEnd = timeEnd
//        this.temperature = temperature
//        this.rainfall = rainfall
//        this.averagePace = averagePace
//        this.maxPace = maxPace
//        this.minPace = minPace
//        this.averageSpeed = averageSpeed
//        this.maxSpeed = maxSpeed
//        this.minSpeed = minSpeed
//        this.averageHeartRate = averageHeartRate
//        this.maxHeartRate = maxHeartRate
//        this.minHeartRate = minHeartRate
//        this.resultSpeed = resultSpeed
//        this.resultTime = resultTime
//        this.resultWeight = resultWeight
//        this.resultAmount = resultAmount
//        this.resultRange = resultRange
//    }


}

