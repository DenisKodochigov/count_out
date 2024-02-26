package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Round
import com.example.count_out.entity.StateWorkOut
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(round: Round, stateService: (StateWorkOut)->Unit){
        speechManager.speech(round.speech.beforeStart, stateService)
        speechManager.speech(round.speech.afterStart, stateService)
        round.exercise.forEach { exercise->
            playerExercise.playingExercise(exercise, stateService)
        }
        speechManager.speech(round.speech.beforeEnd, stateService)
        speechManager.speech(round.speech.afterEnd, stateService)
    }
}