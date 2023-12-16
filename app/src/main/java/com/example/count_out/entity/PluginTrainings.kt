package com.example.count_out.entity

import com.example.count_out.data.room.tables.TrainingDB

object PluginTrainings {
    val list = mutableListOf(
        TrainingDB( idTraining = 0, name = "Training 1", amountActivity = 2),
        TrainingDB( idTraining = 1, name = "Training 2", amountActivity = 5),
        TrainingDB( idTraining = 2, name = "Training 3", amountActivity = 6),
        TrainingDB( idTraining = 3, name = "Training 4", amountActivity = 7),
        TrainingDB( idTraining = 4, name = "Training 5", amountActivity = 8),
        TrainingDB( idTraining = 5, name = "Training 6", amountActivity = 9),
        TrainingDB( idTraining = 6, name = "Training 7", amountActivity = 13),
        TrainingDB( idTraining = 7, name = "Training 8", amountActivity = 22),
        TrainingDB( idTraining = 8, name = "Training 9", amountActivity = 12),
    )
    fun item (id: Long): Training{
        return list.find { it.idTraining == id } ?: TrainingDB() as Training
    }
}