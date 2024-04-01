package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut(template: VariablesInService, variablesOut: VariablesOutService,
    ) {
        if (variablesOut.stateRunning.value == StateRunning.Started) {
            template.getTraining().speech.beforeStart.addMessage = ". " + template.getTraining().name
            speechManager.speech(variablesOut, template.getTraining().speech.beforeStart)
            speechManager.speech(variablesOut, template.getTraining().speech.afterStart)
            template.getTraining().rounds.forEachIndexed { index, _->
                playerRound.playingRound( template.apply { indexRound = index }, variablesOut) }
            speechManager.speech(variablesOut, template.getTraining().speech.beforeEnd)
            speechManager.speech(variablesOut, template.getTraining().speech.afterEnd)
        }
    }
}