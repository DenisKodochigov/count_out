package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Round
import javax.inject.Inject

class PlayerRound @Inject constructor(val speechManager:SpeechManager, val playerExercise: PlayerExercise)
{
    private val delay = 5000L
    suspend fun playingRound(round: Round){
        speechManager.speakOut(round.speech.beforeStart, delay)
        speechManager.speakOut(round.speech.afterStart, delay)
        round.exercise.forEach { exercise->
            playerExercise.playingExercise(exercise)
        }
        speechManager.speakOut(round.speech.beforeEnd, delay)
        speechManager.speakOut(round.speech.afterEnd, delay)
    }
}