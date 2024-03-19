package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        speechManager.speech(variablesOut, template.getRound().speech.beforeStart)
        speechManager.speech(variablesOut, template.getRound().speech.afterStart)
        template.getRound().exercise.forEachIndexed { index, _->
            playerExercise.playingExercise( template.apply { indexExercise = index }, variablesOut)
        }
        speechManager.speech(variablesOut, template.getRound().speech.beforeEnd)
        speechManager.speech(variablesOut, template.getRound().speech.afterEnd)
    }
}