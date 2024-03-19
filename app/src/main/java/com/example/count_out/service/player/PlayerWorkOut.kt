package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ) {
        speechManager.speech(template.getTraining().speech.beforeStart + " " +
                template.getTraining().name, variablesOut)
        speechManager.speech(template.getTraining().speech.afterStart, variablesOut)
        template.getTraining().rounds.forEachIndexed { index, _->
            playerRound.playingRound( template.apply { indexRound = index }, variablesOut)
        }
        speechManager.speech(template.getTraining().speech.beforeEnd, variablesOut )
        speechManager.speech(template.getTraining().speech.afterEnd, variablesOut )
        lg("PlayerWorkOut End workout")
        template.stateRunning.value = StateRunning.Stopped
        variablesOut.stateRunning.value = StateRunning.Stopped
    }
}