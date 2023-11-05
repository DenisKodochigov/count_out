package com.example.count_out.data.room.tables

import com.example.count_out.entity.Coordinate
import com.example.count_out.entity.Count

data class CountDB(
    override val coordinate: Coordinate = CoordinateDB(),
    override val timeL: Long = 0L,
    override val heartRate: Int = 0,
    override val geographicalHeight: Double = 0.0,
    override val sensor1: Double = 0.0,
    override val sensor2: Double = 0.0,
    override val sensor3: Double = 0.0
): Count
