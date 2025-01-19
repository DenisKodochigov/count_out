package com.example.framework.room.db.traking

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.framework.room.db.training.TrainingTable

@Entity(tableName = "tb_workout")
data class WorkoutTable(
    @PrimaryKey(autoGenerate = true)  var idWorkout: Long = 0L,
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
//    @Ignore  val training: Training? = null,
) {

     fun formTraining(training: TrainingTable) {
        this.trainingId = training.idTraining
     }

//     fun formWeather(weather: WeatherSourceImpl) {
//        temperature = weather.temperature2m
//        relativeHumidity2m = weather.relativeHumidity2m
//        apparentTemperature = weather.apparentTemperature
//        precipitation = weather.precipitation
//        rain = weather.rain
//        showers = weather.showers
//        snowfall = weather.snowfall
//        weatherCode = weather.weatherCode
//        cloudCover = weather.cloudCover
//        pressureMsl = weather.pressureMsl
//        surfacePressure = weather.surfacePressure
//        windSpeed10m = weather.windSpeed10m
//        windDirection10m = weather.windDirection10m
//        windGusts10m = weather.windGusts10m
//    }
}