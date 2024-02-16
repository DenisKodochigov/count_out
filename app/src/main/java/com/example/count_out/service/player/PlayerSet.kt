package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Set
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager)
{
    @Inject lateinit var playerExercise: PlayerExercise
    private val delay = 5000L
    suspend fun playingSet(set: Set){
        speechManager.speakOut(set.speech.soundBeforeStart, delay)
        speechManager.speakOut(set.speech.soundAfterStart, delay)
//        round.exercise.forEach { exercise->
//            playerExercise.playingExercise(exercise)
//
        speechManager.speakOut(set.speech.soundBeforeEnd, delay)
        speechManager.speakOut(set.speech.soundAfterEnd, delay)
    }
}