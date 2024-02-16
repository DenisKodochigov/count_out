package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, val playerSet: PlayerSet)
{
    private val delay = 5000L
    suspend fun playingExercise(exercise: Exercise){
        speechManager.speakOut(exercise.speech.soundBeforeStart, delay)
        speechManager.speakOut(exercise.speech.soundAfterStart, delay)
        exercise.sets.forEach { set->
            playerSet.playingSet(set)
        }
        speechManager.speakOut(exercise.speech.soundBeforeEnd, delay)
        speechManager.speakOut(exercise.speech.soundAfterEnd, delay)
    }
}