package com.example.count_out.entity.workout

interface TemporaryBase: Coordinate {
    override val latitude: Double
    override val longitude: Double
    override val altitude: Double
    override val timeLocation: Long
    override val accuracy: Float
    override val speed: Float
    override val distance: Float
    val heartRate: Int
    val idTraining: Long
    val idSet: Long
    val rest: Int
    val activityId: Long
    val runningSet: Int // 0 - end, 1 - running
}