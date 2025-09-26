package com.count_out.framework.room.db.old.training

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.count_out.data.models.old.TrainingImplD
//import com.count_out.domain.entity.workout.Training
//
//@Entity(tableName = "tb_trainings")
//data class TrainingTable(
//    @PrimaryKey(autoGenerate = true)  var idTraining: Long = 0L,
//    var name: String = "",
//    var speechId: Long = 0,
//){
//    constructor(training: Training) : this(
//        idTraining = training.idTraining,
//        name = training.name,
//        speechId = training.speechId
//    )
//    constructor(item: TrainingImplD, speechId: Long = item.speechId, idTraining: Long = item.idTraining): this(
//        idTraining = idTraining,
//        name = item.name,
//        speechId = speechId,
//    )
//}