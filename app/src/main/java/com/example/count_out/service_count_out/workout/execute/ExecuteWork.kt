package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import javax.inject.Inject

class ExecuteWork @Inject constructor(val speechManager:SpeechManager, private val executeRound: ExecuteRound)
{
    suspend fun executeWorkOut(sendToSrv: SendToService, sendToUI: SendToUI ){
//        lg("playingWorkOut ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToSrv.training.value?.speech?.beforeStart?.addMessage = ". " + sendToSrv.training.value?.name
            sendToSrv.training.value?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            sendToSrv.training.value?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }
            sendToSrv.training.value?.rounds?.forEachIndexed { index, _->
                sendToUI.mark.value = sendToUI.mark.value.copy(idRound = index)
                executeRound.executeRound( sendToSrv.apply { indexRound = index }, sendToUI) }
            sendToSrv.training.value?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it)}
            sendToSrv.training.value?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
            sendToUI.runningState.value =RunningState.Stopped
        }
    }
}
