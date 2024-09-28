package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import javax.inject.Inject

class SpeakingWork @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut( sendToWS: SendToWorkService, sendToUI: SendToUI ){
//        lg("playingWorkOut ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToWS.training.value?.speech?.beforeStart?.addMessage = ". " + sendToWS.training.value?.name
            sendToWS.training.value?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            sendToWS.training.value?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }
            sendToWS.training.value?.rounds?.forEachIndexed { index, _->
                sendToUI.mark.value = sendToUI.mark.value.copy(idRound = index)
                playerRound.playingRound( sendToWS.apply { indexRound = index }, sendToUI) }
            sendToWS.training.value?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it)}
            sendToWS.training.value?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
            sendToUI.runningState.value =RunningState.Stopped
        }
    }
}
