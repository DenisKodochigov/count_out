package com.example.count_out.service_count_out.work.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteWork @Inject constructor(
    val speechManager:SpeechManager, private val executeRound: ExecuteRound) {
    suspend fun executeWorkOut(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.trap()
        dataForWork.training.value?.let { training->
            training.speech.beforeStart.addMessage = training.name
            speechManager.speech(dataFromWork, training.speech.beforeStart)
            speechManager.speech(dataFromWork, training.speech.afterStart)
            training.rounds.forEachIndexed { index, _->
                executeRound.executeRound( dataForWork.apply { indexRound = index }, dataFromWork) }
            speechManager.speech(dataFromWork, training.speech.beforeEnd)
            speechManager.speech(dataFromWork, training.speech.afterEnd)
            dataFromWork.runningState.value = RunningState.Stopped
        }
    }
}
