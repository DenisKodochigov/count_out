package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.StateWorkOut
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, private val playerSet: PlayerSet)
{
    private val show = false
    suspend fun playingExercise(exercise: Exercise,
                                pause: MutableState<Boolean>, stateService: (StateWorkOut)->Unit){
        speechManager.speech(exercise.speech.beforeStart + " " + exercise.activity.name, pause, stateService)
        speechManager.speech(exercise.activity.description, pause, stateService)
        speechManager.speech(exercise.speech.afterStart, pause, stateService)
        exercise.sets.forEach { set->
            playerSet.playingSet(set, pause, stateService)
        }
        speechManager.speech(exercise.speech.beforeEnd, pause, stateService)
        speechManager.speech(exercise.speech.afterEnd, pause, stateService)
    }
}