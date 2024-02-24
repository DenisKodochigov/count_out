package com.example.count_out.service.player

import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, val playerSet: PlayerSet)
{
    private val show = false
    suspend fun playingExercise(exercise: Exercise, stateService: MutableStateFlow<StateWorkOutDB>){
        speech(exercise.speech.beforeStart + " " + exercise.activity.name, stateService)
        speech(exercise.activity.description, stateService)
        speech(exercise.speech.afterStart, stateService)
        exercise.sets.forEach { set->
            playerSet.playingSet(set, stateService)
        }
        speech(exercise.speech.beforeEnd, stateService)
        speech(exercise.speech.afterEnd, stateService)
    }
    suspend fun speech(text: String, stateService: MutableStateFlow<StateWorkOutDB>){
        if (text.length > 1) {
            stateService.value = StateWorkOutDB(state = text)
            log(show, "PlayerExercise stateService: ${stateService.value}")
            speechManager.speakOut(text, 0L)
        }
    }
}