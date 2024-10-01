package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import javax.inject.Inject

class ExecuteWork @Inject constructor(val speechManager:SpeechManager, private val executeRound: ExecuteRound)
{
    suspend fun executeWorkOut(sendToSrv: DataForServ, dataForUI: DataForUI ){
//        lg("playingWorkOut ${sendToUI.runningState.value}")
        if (dataForUI.runningState.value == RunningState.Started) {
            sendToSrv.training.value?.speech?.beforeStart?.addMessage = ". " + sendToSrv.training.value?.name
            sendToSrv.training.value?.speech?.beforeStart?.let { speechManager.speech(dataForUI, it) }
            sendToSrv.training.value?.speech?.afterStart?.let { speechManager.speech(dataForUI, it) }
            sendToSrv.training.value?.rounds?.forEachIndexed { index, _->
                dataForUI.mark.value = dataForUI.mark.value.copy(idRound = index)
                executeRound.executeRound( sendToSrv.apply { indexRound = index }, dataForUI) }
            sendToSrv.training.value?.speech?.beforeEnd?.let { speechManager.speech(dataForUI, it)}
            sendToSrv.training.value?.speech?.afterEnd?.let { speechManager.speech(dataForUI, it) }
            dataForUI.runningState.value =RunningState.Stopped
        }
    }
}
