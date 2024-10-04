package com.example.count_out.service_count_out.work.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteWorkN @Inject constructor(val speechManager:SpeechManager, private val executeRound: ExecuteRoundN)
{
    suspend fun executeWorkOut(dataForWork: DataForWork, dataFromWork: DataFromWork){
        if (dataFromWork.runningState.value == RunningState.Stopped) dataFromWork.cancelCoroutineWork()
        dataForWork.training.value?.speech?.beforeStart?.addMessage = ". " + dataForWork.training.value?.name
        dataForWork.training.value?.speech?.beforeStart?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.training.value?.speech?.afterStart?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.training.value?.rounds?.forEachIndexed { index, _->
            dataFromWork.mark.value = dataFromWork.mark.value.copy(idRound = index)
            executeRound.executeRound( dataForWork.apply { indexRound = index }, dataFromWork) }
        dataForWork.training.value?.speech?.beforeEnd?.let { speechManager.speech(dataFromWork, it)}
        dataForWork.training.value?.speech?.afterEnd?.let { speechManager.speech(dataFromWork, it) }
        dataFromWork.runningState.value = RunningState.Stopped
    }
}