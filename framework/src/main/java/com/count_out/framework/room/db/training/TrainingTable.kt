package com.count_out.framework.room.db.training

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.TrainingImplD
import com.count_out.domain.entity.workout.Training


@Entity(tableName = "tb_trainings")
data class TrainingTable(
    @PrimaryKey(autoGenerate = true)  var idTraining: Long = 0L,
    var name: String = "",
    var isSelected: Boolean = false,
    var speechId: Long = 0,
//    @Ignore  var amountActivity: Int = 0,
//    @Ignore  var speech: SpeechKit = SpeechKitTable(),
//    @Ignore  var rounds: List<Round> = emptyList(),
){
    constructor(training: Training) : this(
        idTraining = training.idTraining,
        name = training.name,
        isSelected = training.isSelected,
        speechId = training.speechId
    )
    constructor(item: TrainingImplD, speechId: Long = item.speechId, idTraining: Long = item.idTraining): this(
        idTraining = idTraining,
        name = item.name,
        isSelected = item.isSelected,
        speechId = speechId,
    )
}