package com.example.count_out.data.room.tables

import com.example.count_out.entity.Counts
import com.example.count_out.entity.Rainfall
import com.example.count_out.entity.Round
import com.example.count_out.entity.Workout

data class WorkoutDB(
    override val idWorkout: Long = 0,
    override var name: String = "",
    override val listRound: List<Round> = emptyList(),
    override val open: Boolean = false,
    override val timeStart: Int = 0,
    override val timeEnd: Int = 0,
    override val temperature: Double = 20.0,
    override val rainfall: Rainfall = Rainfall.MEDIUM,
    override val counts: Counts = CountsDB(),
    override val averagePace: Double = 0.0,
    override val maxPace: Double = 0.0,
    override val minPace: Double = 0.0,
    override val averageSpeed: Double = 0.0,
    override val maxSpeed: Double = 0.0,
    override val minSpeed: Double = 0.0,
    override val averageHeartRate: Double = 0.0,
    override val maxHeartRate: Double = 0.0,
    override val minHeartRate: Double = 0.0,
    override val resultSpeed: Double = 0.0,
    override val resultTime: Double = 0.0,
    override val resultWeight: Double = 0.0,
    override val resultAmount: Double = 0.0,
    override val resultRange: Double = 0.0,
) : Workout

