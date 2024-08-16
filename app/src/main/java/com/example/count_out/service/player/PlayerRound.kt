package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.StateRunning
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(
        template: SendToWorkService,
        variablesOut: SendToUI,
    ){
        if (variablesOut.stateRunning.value == StateRunning.Started) {
            template.getRound()?.speech?.beforeStart?.let { speechManager.speech(variablesOut, it) }
            template.getRound()?.speech?.afterStart?.let { speechManager.speech(variablesOut, it) }
            template.getRound()?.exercise?.forEachIndexed { index, _->
                playerExercise.playingExercise( template.apply { indexExercise = index }, variablesOut) }
            template.getRound()?.speech?.beforeEnd?.let { speechManager.speech(variablesOut, it) }
            template.getRound()?.speech?.afterEnd?.let { speechManager.speech(variablesOut, it) }
        }
    }
}