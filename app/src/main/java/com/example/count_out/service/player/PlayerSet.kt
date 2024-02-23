package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Set
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager)
{
    @Inject lateinit var playerExercise: PlayerExercise

    suspend fun playingSet(set: Set){
        speechManager.speakOut(set.speech.beforeStart, 0L)
        speechManager.speakOut(set.speech.afterStart, 0L)
        if (set.reps > 0) {
            for (count in 1..set.reps){
                speechManager.speakOutFlush(text = count.toString())
                delay((set.intervalReps * 1000).toLong())
            }
        } else if (set.duration > 0){
            delay((set.duration * 60 * 1000).toLong())
        }
        speechManager.speakOut(set.speech.beforeEnd, 0L)
        speechManager.speakOut(set.speech.afterEnd, 0L)
    }
}