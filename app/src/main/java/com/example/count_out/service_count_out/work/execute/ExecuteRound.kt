package com.example.count_out.service_count_out.work.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteRound @Inject constructor(
    val speechManager:SpeechManager, private val executeExercise: ExecuteExercise ){
    suspend fun executeRound(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        dataForWork.getRound()?.speech?.beforeStart?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getRound()?.speech?.afterStart?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getRound()?.exercise?.forEachIndexed { index, _->
            executeExercise.executeExercise( dataForWork.apply { indexExercise = index }, dataFromWork) }
        dataForWork.getRound()?.speech?.beforeEnd?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getRound()?.speech?.afterEnd?.let { speechManager.speech(dataFromWork, it) }
    }
}