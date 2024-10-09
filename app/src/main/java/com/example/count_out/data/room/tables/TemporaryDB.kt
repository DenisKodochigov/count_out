package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.TemporaryBase

@Entity(tableName = "tb_temporary")
data class TemporaryDB(
    @PrimaryKey(autoGenerate = true) override var id: Long = 0,
    override val latitude: Double = 0.0,
    override val longitude: Double = 0.0,
    override val altitude: Double = 0.0,
    override val time: Long = 0L,
    override val accuracy: Float = 0f,
    override val speed: Float = 0f,
    override val heartRate: Int = 0,
    override val idTraining: Long = 0,
    override val idSet: Long = 0,
    override val runningSet: Int = 0,  // 0 - end, 1 - running
): TemporaryBase
