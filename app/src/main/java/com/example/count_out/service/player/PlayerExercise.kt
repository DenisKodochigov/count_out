package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.StateWorkOut
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, val playerSet: PlayerSet)
{
    private val show = false
    suspend fun playingExercise(exercise: Exercise, stateService: (StateWorkOut)->Unit){
        speechManager.speech(exercise.speech.beforeStart + " " + exercise.activity.name, stateService)
        speechManager.speech(exercise.activity.description, stateService)
        speechManager.speech(exercise.speech.afterStart, stateService)
        exercise.sets.forEach { set->
            playerSet.playingSet(set, stateService)
        }
        speechManager.speech(exercise.speech.beforeEnd, stateService)
        speechManager.speech(exercise.speech.afterEnd, stateService)
    }
}