package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StreamsWorkout
import com.example.count_out.entity.Training
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut(
        training: Training,
        pause: MutableState<Boolean>,
        streamsWorkout: StreamsWorkout
    )
    {
        speechManager.speech(training.speech.beforeStart + " " + training.name, pause, streamsWorkout)
        speechManager.speech(training.speech.afterStart, pause, streamsWorkout )
        training.rounds.forEach { round->
            playerRound.playingRound(round, pause, streamsWorkout)
        }
        speechManager.speech(training.speech.beforeEnd, pause, streamsWorkout )
        speechManager.speech(training.speech.afterEnd, pause, streamsWorkout )
    }
}