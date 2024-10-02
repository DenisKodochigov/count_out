package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.RunningState
import javax.inject.Inject

class ExecuteWork @Inject constructor(val speechManager:SpeechManager, private val executeRound: ExecuteRound)
{
    suspend fun executeWorkOut(dataForWork: DataForServ, dataForUI: DataForUI ){
        if (dataForUI.runningState.value == RunningState.Stopped) dataForUI.cancelCoroutineWork()
        dataForWork.training.value?.speech?.beforeStart?.addMessage = ". " + dataForWork.training.value?.name
        dataForWork.training.value?.speech?.beforeStart?.let { speechManager.speech(dataForUI, it) }
        dataForWork.training.value?.speech?.afterStart?.let { speechManager.speech(dataForUI, it) }
        dataForWork.training.value?.rounds?.forEachIndexed { index, _->
            dataForUI.mark.value = dataForUI.mark.value.copy(idRound = index)
            executeRound.executeRound( dataForWork.apply { indexRound = index }, dataForUI) }
        dataForWork.training.value?.speech?.beforeEnd?.let { speechManager.speech(dataForUI, it)}
        dataForWork.training.value?.speech?.afterEnd?.let { speechManager.speech(dataForUI, it) }
        dataForUI.runningState.value = RunningState.Stopped
    }
}
