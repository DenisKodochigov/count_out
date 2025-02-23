package com.count_out.framework.room.db.training

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.TrainingImpl


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
    fun fromTrainingSource(training: TrainingImpl) = TrainingTable(
        name = training.name,
        isSelected = training.isSelected,
        speechId = training.speechId
    )
}