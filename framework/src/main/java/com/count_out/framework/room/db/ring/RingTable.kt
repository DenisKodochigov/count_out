package com.count_out.framework.room.db.ring

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.RingImpl

@Entity(tableName = "tb_ring")
data class RingTable(
    @PrimaryKey(autoGenerate = true) var idRing: Long = 0L,
    var name: String = "",
    var countRing: Int = 0,
    var trainingId: Long = 0,
    var speechId: Long = 0,
){
    constructor(item: RingImpl, speechId: Long = item.speechId, idRing: Long = item.idRing): this(
        idRing = idRing,
        name = item.name,
        countRing = item.countRing,
        speechId = speechId,
        trainingId = item.trainingId,
    )
}