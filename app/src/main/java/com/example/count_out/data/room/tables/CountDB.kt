package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.no_use.Coordinate
import com.example.count_out.entity.no_use.Count
@Entity(tableName = "tb_counts")
data class CountDB(
    @PrimaryKey(autoGenerate = true) override val idCount: Long = 0L,
    override val coordinate: Coordinate = CoordinateDB(),
    override val workoutId: Long = 0L,
    override val timeL: Long = 0L,
    override val heartRate: Int = 0,
    override val geographicalHeight: Double = 0.0,
    override val sensor1: Double = 0.0,
    override val sensor2: Double = 0.0,
    override val sensor3: Double = 0.0
): Count
