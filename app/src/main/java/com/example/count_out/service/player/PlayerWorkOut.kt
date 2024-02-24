package com.example.count_out.service.player

import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    private val show = false
    suspend fun playingWorkOut(training: Training, stateService: MutableStateFlow<StateWorkOutDB>){
        speech(training.speech.beforeStart + " " + training.name, stateService)
        speech(training.speech.afterStart, stateService )
        training.rounds.forEach { round->
            playerRound.playingRound(round, stateService)
        }
        speech(training.speech.beforeEnd, stateService )
        speech(training.speech.afterEnd, stateService )
    }

    suspend fun speech(text: String, stateService: MutableStateFlow<StateWorkOutDB>){
        if (text.length > 1) {
            stateService.value = StateWorkOutDB(state = text)
            log(show, "PlayerWorkOut stateWorkOut: ${stateService.value}")
            speechManager.speakOut(text, 0L)
        }
    }
}