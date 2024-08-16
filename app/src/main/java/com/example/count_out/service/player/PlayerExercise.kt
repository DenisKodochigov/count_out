package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.StateRunning
import javax.inject.Inject

class PlayerExercise @Inject constructor(
    val speechManager:SpeechManager,
    private val playerSet: PlayerSet,)
{
    suspend fun playingExercise(
        template: SendToWorkService,
        variablesOut: SendToUI,
    ){
        if (variablesOut.stateRunning.value == StateRunning.Started) {
            template.getExercise()?.speech?.beforeStart?.addMessage = template.getExercise()?.activity?.name + "."
            if (template.enableSpeechDescription.value)
                template.getExercise()?.speech?.beforeStart?.addMessage += template.getExercise()?.activity?.description
            template.getExercise()?.speech?.beforeStart?.let { speechManager.speech(variablesOut, it)}
            template.getExercise()?.speech?.afterStart?.let { speechManager.speech(variablesOut,it) }
            template.getExercise()?.sets?.forEachIndexed { index, _->
                playerSet.playingSet(template.apply { indexSet = index }, variablesOut)
            }
//            speechManager.speech(variablesOut, template.getExercise().speech.beforeEnd)
//            speechManager.speech(variablesOut, template.getExercise().speech.afterEnd)
        }
    }
}
