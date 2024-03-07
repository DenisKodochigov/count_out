package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Round
import com.example.count_out.entity.StateWorkOut
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(round: Round,
                             pause: MutableState<Boolean>, stateService: (StateWorkOut)->Unit){
        speechManager.speech(round.speech.beforeStart, pause, stateService)
        speechManager.speech(round.speech.afterStart, pause, stateService)
        round.exercise.forEach { exercise->
            playerExercise.playingExercise(exercise, pause, stateService)
        }
        speechManager.speech(round.speech.beforeEnd, pause, stateService)
        speechManager.speech(round.speech.afterEnd, pause, stateService)
    }
}