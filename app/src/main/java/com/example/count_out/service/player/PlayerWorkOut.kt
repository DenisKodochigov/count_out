package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.Training
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut(
        training: Training,
        pause: MutableState<Boolean>,
        stateService: (StateWorkOut)->Unit)
    {
        speechManager.speech(training.speech.beforeStart + " " + training.name, pause, stateService)
        speechManager.speech(training.speech.afterStart, pause, stateService )
        training.rounds.forEach { round->
            playerRound.playingRound(round, pause, stateService)
        }
        speechManager.speech(training.speech.beforeEnd, pause, stateService )
        speechManager.speech(training.speech.afterEnd, pause, stateService )
    }
}