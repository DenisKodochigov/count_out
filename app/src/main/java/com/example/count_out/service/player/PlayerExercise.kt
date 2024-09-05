package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject

class PlayerExercise @Inject constructor(
    val speechManager:SpeechManager,
    private val playerSet: PlayerSet,)
{
    suspend fun playingExercise(
        template: SendToWorkService,
        sendToUI: SendToUI,
    ){
        lg("playingExercise ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            template.getExercise()?.speech?.beforeStart?.addMessage = template.getExercise()?.activity?.name + "."
            if (template.enableSpeechDescription.value)
                template.getExercise()?.speech?.beforeStart?.addMessage += template.getExercise()?.activity?.description
            template.getExercise()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it)}
            template.getExercise()?.speech?.afterStart?.let { speechManager.speech(sendToUI,it) }
            template.getExercise()?.sets?.forEachIndexed { index, _->
                playerSet.playingSet( template.apply { indexSet = index }, sendToUI)
            }
//            speechManager.speech(variablesOut, template.getExercise().speech.beforeEnd)
//            speechManager.speech(variablesOut, template.getExercise().speech.afterEnd)
        }
    }
}
