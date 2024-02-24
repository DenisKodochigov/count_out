package com.example.count_out.service.player

import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Round
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    private val show = false
    suspend fun playingRound(round: Round, stateService: MutableStateFlow<StateWorkOutDB>){
        speech(round.speech.beforeStart, stateService)
        speech(round.speech.afterStart, stateService)
        round.exercise.forEach { exercise->
            playerExercise.playingExercise(exercise, stateService)
        }
        speech(round.speech.beforeEnd, stateService)
        speech(round.speech.afterEnd, stateService)
    }
    suspend fun speech(text: String, stateService: MutableStateFlow<StateWorkOutDB>){
        if (text.length > 1) {
            stateService.value = StateWorkOutDB(state = text)
            log(show, "PlayerRound stateWorkOut: ${stateService.value}")
            speechManager.speakOut(text, 0L)
        }
    }
}