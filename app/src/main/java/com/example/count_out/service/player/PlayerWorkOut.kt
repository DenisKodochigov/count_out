package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.Training
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut(training: Training, stateService: (StateWorkOut)->Unit){
        speechManager.speech(training.speech.beforeStart + " " + training.name, stateService)
        speechManager.speech(training.speech.afterStart, stateService )
        training.rounds.forEach { round->
            playerRound.playingRound(round, stateService)
        }
        speechManager.speech(training.speech.beforeEnd, stateService )
        speechManager.speech(training.speech.afterEnd, stateService )
    }
}