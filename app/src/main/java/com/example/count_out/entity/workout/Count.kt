package com.example.count_out.entity.workout

interface Count: TemporaryBase {
    val idCount: Long
    val workoutId: Long
    override val latitude: Double
    override val longitude: Double
    override val altitude: Double
    override val timeLocation: Long
    override val accuracy: Float
    override val speed: Float
    override val distance: Float
    override val heartRate: Int
    override val idTraining: Long
    override val idSet: Long
    override val rest: Int
    override val activityId: Long
    override val runningSet: Int // 0 - end, 1 - running
    val sensor1: Double
    val sensor2: Double
    val sensor3: Double

//    fun add(workoutId: Long, temporary: TemporaryDB): Count
}