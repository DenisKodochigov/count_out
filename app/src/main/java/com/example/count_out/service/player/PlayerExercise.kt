package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, private val playerSet: PlayerSet)
{
    suspend fun playingExercise(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        speechManager.speech(template.getExercise().speech.beforeStart + " " +
                template.getExercise().activity.name, variablesOut)
        speechManager.speech(template.getExercise().activity.description, variablesOut)
        speechManager.speech(template.getExercise().speech.afterStart, variablesOut)
        template.getExercise().sets.forEachIndexed { index, _->
            playerSet.playingSet(template.apply { indexSet = index }, variablesOut)
        }
        speechManager.speech(template.getExercise().speech.beforeEnd, variablesOut)
        speechManager.speech(template.getExercise().speech.afterEnd, variablesOut)
    }
}
