package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Coordinate
import com.example.count_out.entity.Count
@Entity(tableName = "tb_counts")
data class CountDB(
    @PrimaryKey(autoGenerate = true) override var idCount: Long = 0L,
    override var workoutId: Long = 0L,
    override var coordinateId: Long = 0L,
    override var timeL: Long = 0L,
    override var heartRate: Int = 0,
    override var geographicalHeight: Double = 0.0,
    override var sensor1: Double = 0.0,
    override var sensor2: Double = 0.0,
    override var sensor3: Double = 0.0,
    @Ignore override var coordinate: Coordinate = CoordinateDB(),
): Count
