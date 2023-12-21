package com.example.count_out.entity

import com.example.count_out.R
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB

object Plugins
{
    val listRound = mutableListOf<RoundDB>()
    val listTr = mutableListOf<TrainingDB>()
    val listEx = mutableListOf<ExerciseDB>()
    val listSpeech = mutableListOf<SpeechDB>()
    val listActivity = listOf<Activity>(
        ActivityDB(idActivity = 0, name = "Run", icon = R.drawable.ic_setka),
        ActivityDB(idActivity = 1, name = "Ski", icon = R.drawable.ic_setka))

    val listSets = mutableListOf<Set>()
    fun init(){
        var idR = 0L
        for (t in 0..10L){
            listRound.add(RoundDB( idRound = idR++, trainingId = t, roundType = RoundType.UP, exercise = mutableListOf()))
            listRound.add(RoundDB( idRound = idR++, trainingId = t, roundType = RoundType.OUT, exercise = mutableListOf()))
            listRound.add(RoundDB( idRound = idR++, trainingId = t, roundType = RoundType.DOWN, exercise = mutableListOf()))
            listTr.add(
                TrainingDB( idTraining = t,
                    name = "Training $t",
                    rounds = mutableListOf(listRound[listRound.size-3], listRound[listRound.size-2], listRound[listRound.size-1]))
            )
        }
    }


    fun item (id: Long): Training{
        return listTr.find { it.idTraining == id } ?: TrainingDB() as Training
    }
}