package com.example.framework.room.db.traking

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_temporary")
data class TemporaryTable(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
     val latitude: Double = 0.0,
     val longitude: Double = 0.0,
     val altitude: Double = 0.0,
     val timeLocation: Long = 0L,
     val accuracy: Float = 0f,
     val speed: Float = 0f,
     val distance: Float = 0f,
     val heartRate: Int = 0,
     val idTraining: Long = 0,
     val idSet: Long = 0,
     val phaseWorkout: Int = 0,  // 0 - rest, 1 - running
     val activityId: Long = 0L,
)