package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.Training
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    private val show = true
    suspend fun playingWorkOut(
        training: Training,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ) {
        speechManager.speech(training.speech.beforeStart + " " + training.name, pause, flowStateServiceMutable)
        speechManager.speech(training.speech.afterStart, pause, flowStateServiceMutable )
        training.rounds.forEach { round->
            playerRound.playingRound(round, pause, flowStateServiceMutable)
        }
        speechManager.speech(training.speech.beforeEnd, pause, flowStateServiceMutable )
        speechManager.speech(training.speech.afterEnd, pause, flowStateServiceMutable )
    }
}