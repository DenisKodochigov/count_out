package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut( template: SendToWorkService, sendToUI: SendToUI ){
        lg("playingWorkOut ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            template.training.value?.speech?.beforeStart?.addMessage = ". " + template.training.value?.name
            template.training.value?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            lg("playingWorkOut after BEFORE")
            template.training.value?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }
            template.training.value?.rounds?.forEachIndexed { index, _->
                playerRound.playingRound( template.apply { indexRound = index }, sendToUI) }
            template.training.value?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it)}
            template.training.value?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
            sendToUI.runningState.value =RunningState.Stopped
        }
    }
}