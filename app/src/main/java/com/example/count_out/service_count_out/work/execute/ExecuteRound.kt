package com.example.count_out.service_count_out.work.execute

import android.content.Context
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteRound @Inject constructor(
    val speechManager:SpeechManager, private val executeExercise: ExecuteExercise, val context: Context
){
    suspend fun executeRound(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        dataForWork.getRound()?.let { round->
            speechManager.speech(dataFromWork, round.speech.beforeStart)
            speechManager.speech(dataFromWork, round.speech.afterStart)
            round.exercise.forEachIndexed { index, _->
                executeExercise.executeExercise( dataForWork.apply { indexExercise = index }, dataFromWork) }
            speechManager.speech(dataFromWork, round.speech.beforeEnd)
            speechManager.speech(dataFromWork, round.speech.afterEnd)
        }
    }
}