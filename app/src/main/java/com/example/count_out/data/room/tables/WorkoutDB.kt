package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Rainfall
import com.example.count_out.entity.Training
import com.example.count_out.entity.no_use.Workout
@Entity(tableName = "tb_workout")
data class WorkoutDB(
    @PrimaryKey(autoGenerate = true) override var idWorkout: Long = 0L,
    override var name: String = "",
    override var rainfallId: Int = 0,
    override var timeStart: Int = 0,
    override var timeEnd: Int = 0,
    override var temperature: Double = 0.0,
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
    override var trainingId: Long = 0,
    override var isSelected: Boolean = false,
    @Ignore override val training: Training? = null,
    @Ignore override var rainfall: Rainfall = Rainfall.MEDIUM,
) : Workout {
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

