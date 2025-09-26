package com.count_out.framework.room.db.old.round
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.count_out.data.models.old.RoundImplD

//@Entity(tableName = "tb_round")
//data class RoundTable(
//    @PrimaryKey(autoGenerate = true) var idRound: Long = 0L,
//    var trainingId: Long = 0L,
//    var speechId: Long = 0L,
//    var roundType: Int = 0,
//    var numberLaps: Int = 1,
//){
//    constructor(item: RoundImplD, speechId: Long = item.speechId, idRound: Long = item.idRound): this(
//        idRound = idRound,
//        trainingId = item.trainingId,
//        speechId = speechId,
//        roundType = item.roundType.ordinal,
//    )
//}