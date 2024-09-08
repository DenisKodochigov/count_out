package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(sendToWS: SendToWorkService, sendToUI: SendToUI, ){
//        lg("playingRound ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToWS.getRound()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            sendToWS.getRound()?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }
            sendToWS.getRound()?.exercise?.forEachIndexed { index, _->
                playerExercise.playingExercise( sendToWS.apply { indexExercise = index }, sendToUI) }
            sendToWS.getRound()?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it) }
            sendToWS.getRound()?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
        }
    }
}