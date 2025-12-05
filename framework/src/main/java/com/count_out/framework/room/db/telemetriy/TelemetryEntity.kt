package com.count_out.framework.room.db.telemetriy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "telemetry_tb")
data class TelemetryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val lat: Double,
    val lon: Double,
    val heartRate: Int?,
    val synced: Boolean = false
){
    fun toDto(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "timestamp" to timestamp,
            "lat" to lat,
            "lon" to lon ,
            "hr" to (heartRate ?: -1)
        )
    }
}
