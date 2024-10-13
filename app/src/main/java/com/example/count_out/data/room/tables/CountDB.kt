package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.workout.Count

@Entity(tableName = "tb_counts")
data class CountDB(
    @PrimaryKey(autoGenerate = true) override var idCount: Long = 0L,
    override var workoutId: Long = 0L,
    override var heartRate: Int = 0,
    override val latitude: Double = 0.0,
    override val longitude: Double = 0.0,
    override val altitude: Double = 0.0,
    override val timeLocation: Long = 0L,
    override val accuracy: Float = 0f,
    override val speed: Float = 0f,
    override val distance: Float = 0f,
    override val idTraining: Long = 0L,
    override val idSet: Long = 0L,
    override val rest: Int = 0,
    override val activityId: Long = 0L,
    override val runningSet: Int = 0,
    override var sensor1: Double = 0.0,
    override var sensor2: Double = 0.0,
    override var sensor3: Double = 0.0,
): Count {
    override fun add(workoutId: Long, temporary: TemporaryDB): CountDB{
        return  CountDB(
            workoutId = workoutId,
            latitude = temporary.latitude,
            longitude = temporary.longitude,
            altitude = temporary.altitude,
            accuracy = temporary.accuracy,
            timeLocation = temporary.timeLocation,
            speed = temporary.speed,
            distance = temporary.distance,
            heartRate = temporary.heartRate,
            idTraining = temporary.idTraining,
            idSet = temporary.idSet,
            rest = temporary.rest,
            activityId = temporary.activityId,
            runningSet = temporary.runningSet,
        )
    }
}
