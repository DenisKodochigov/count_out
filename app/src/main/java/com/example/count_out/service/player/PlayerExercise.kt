package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, val playerSet: PlayerSet)
{
    suspend fun playingExercise(exercise: Exercise){
        speechManager.speakOut(exercise.speech.beforeStart + " " + exercise.activity.name, 0L)
        speechManager.speakOut(exercise.activity.description, 0L)
        speechManager.speakOut(exercise.speech.afterStart, 0L)
        exercise.sets.forEach { set->
            playerSet.playingSet(set)
        }
        speechManager.speakOut(exercise.speech.beforeEnd, 0L)
        speechManager.speakOut(exercise.speech.afterEnd, 0L)
    }
}