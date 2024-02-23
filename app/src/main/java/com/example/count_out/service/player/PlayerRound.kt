package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Round
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(round: Round){
        speechManager.speakOut(round.speech.beforeStart, 0L)
        speechManager.speakOut(round.speech.afterStart, 0L)
        round.exercise.forEach { exercise->
            playerExercise.playingExercise(exercise)
        }
        speechManager.speakOut(round.speech.beforeEnd, 0L)
        speechManager.speakOut(round.speech.afterEnd, 0L)
    }
}