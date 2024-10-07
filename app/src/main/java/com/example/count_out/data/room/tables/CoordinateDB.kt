package com.example.count_out.data.room.tables

import com.example.count_out.entity.Coordinate

data class CoordinateDB(
    override val latitude: Double = 0.0,
    override val longitude: Double = 0.0,
    override val altitude: Double = 0.0,
    override val time: Long = 0L,
    override val accuracy: Float = 0f,
    override val speed: Float = 0f,
): Coordinate
