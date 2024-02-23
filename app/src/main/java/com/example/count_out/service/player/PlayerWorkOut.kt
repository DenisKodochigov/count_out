package com.example.count_out.service.player

import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    val stateWorkOut: MutableStateFlow<StateWorkOut> = MutableStateFlow(StateWorkOutDB())
    suspend fun playingWorkOut(training: Training){
        speech(training.speech.beforeStart + " " + training.name)
        speech(training.speech.afterStart )
        training.rounds.forEach { round->
            playerRound.playingRound(round)
        }
        speech(training.speech.beforeEnd )
        speech(training.speech.afterEnd )
    }

    suspend fun speech(text: String){
        if (text.length > 1) {
            stateWorkOut.value = StateWorkOutDB(state = text)
            log(true, "PlayerWorkOut stateWorkOut: ${stateWorkOut.value}")
            speechManager.speakOut(text, 0L)
        }
    }
}