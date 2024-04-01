package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, private val playerSet: PlayerSet)
{
    suspend fun playingExercise(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        if (variablesOut.stateRunning.value == StateRunning.Started) {
            template.getExercise().speech.beforeStart.addMessage = template.getExercise().activity.name + "."
            if (template.enableSpeechDescription.value)
                template.getExercise().speech.beforeStart.addMessage += template.getExercise().activity.description
            speechManager.speech(variablesOut, template.getExercise().speech.beforeStart)
            speechManager.speech(variablesOut, template.getExercise().speech.afterStart)
            template.getExercise().sets.forEachIndexed { index, _->
                playerSet.playingSet(template.apply { indexSet = index }, variablesOut)
            }
            speechManager.speech(variablesOut, template.getExercise().speech.beforeEnd)
            speechManager.speech(variablesOut, template.getExercise().speech.afterEnd)
        }
    }
}
