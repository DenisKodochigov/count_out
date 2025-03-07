package com.example.count_out.framework.room.db.ring

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_ring")
data class RingTable(
    @PrimaryKey(autoGenerate = true) var idRing: Long = 0L,
    var name: String = "",
    var countRing: Int = 0,
    var trainingId: Long = 0,
    var speechId: Long = 0,
    var amount: Int = 0,
    var durationValue: Double = 0.0,
    var durationUnit: Int = 0

)