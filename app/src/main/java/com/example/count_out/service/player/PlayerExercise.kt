package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import javax.inject.Inject

class PlayerExercise @Inject constructor(
    val speechManager:SpeechManager,
    private val playerSet: PlayerSet,)
{
    suspend fun playingExercise(sendToWS: SendToWorkService, sendToUI: SendToUI, ){
//        lg("playingExercise ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToWS.getExercise()?.speech?.beforeStart?.addMessage = sendToWS.getExercise()?.activity?.name + "."
            if (sendToWS.enableSpeechDescription.value)
                sendToWS.getExercise()?.speech?.beforeStart?.addMessage += sendToWS.getExercise()?.activity?.description
            sendToWS.getExercise()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it)}
            sendToWS.getExercise()?.speech?.afterStart?.let { speechManager.speech(sendToUI,it) }
            sendToWS.getExercise()?.sets?.forEachIndexed { index, _->
                playerSet.playingSet( sendToWS.apply { indexSet = index }, sendToUI)
            }
//            speechManager.speech(variablesOut, sendToWS.getExercise().speech.beforeEnd)
//            speechManager.speech(variablesOut, sendToWS.getExercise().speech.afterEnd)
        }
    }
}
