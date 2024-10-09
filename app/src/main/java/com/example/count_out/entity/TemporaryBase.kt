package com.example.count_out.entity

interface TemporaryBase: Coordinate {
    var id: Long
    override val latitude: Double
    override val longitude: Double
    override val altitude: Double
    override val time: Long
    override val accuracy: Float
    override val speed: Float
    val heartRate: Int
    val idTraining: Long
    val idSet: Long
    val runningSet: Int // 0 - end, 1 - running
}