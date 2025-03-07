package com.example.count_out.framework.room.db.round

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_round")
data class RoundTable(
    @PrimaryKey(autoGenerate = true) var idRound: Long = 0L,
    var trainingId: Long = 0,
    var speechId: Long = 0,
    var roundType: Int = 0,
    var amount: Int = 0,
    var durationValue: Double = 0.0,
    var durationUnit: Int = 0
)