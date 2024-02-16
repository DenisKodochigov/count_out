package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Training
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, val playerRound: PlayerRound)
{
    private val delay = 5000L
    suspend fun playingWorkOut(training: Training){
        if (training.speech.soundBeforeStart.isNotEmpty()) {

        }
        speechManager.speakOut(training.speech.soundBeforeStart, delay)
        speechManager.speakOut(training.speech.soundAfterStart, delay)
        training.rounds.forEach { round->
            playerRound.playingRound(round)
        }
        speechManager.speakOut(training.speech.soundBeforeEnd, delay)
        speechManager.speakOut(training.speech.soundAfterEnd, delay)
    }
}