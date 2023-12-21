package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Round
import com.example.count_out.entity.Speech
import com.example.count_out.entity.Training
@Entity(tableName = "tb_trainings")
data class TrainingDB(
    @PrimaryKey(autoGenerate = true) override var idTraining: Long = 0L,
    override var name: String = "",
    override val isSelected: Boolean = false,
    override var speechId: Long = 0,
    @Ignore override val amountActivity: Int = 0,
    @Ignore override var speech: Speech = SpeechDB(),
    @Ignore override val rounds: List<Round> = emptyList(),
) : Training {
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

