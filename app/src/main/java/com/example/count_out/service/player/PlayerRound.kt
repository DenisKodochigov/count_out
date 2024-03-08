package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Round
import com.example.count_out.entity.StreamsWorkout
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(
        round: Round,
        pause: MutableState<Boolean>, streamsWorkout: StreamsWorkout
    ){
        speechManager.speech(round.speech.beforeStart, pause, streamsWorkout)
        speechManager.speech(round.speech.afterStart, pause, streamsWorkout)
        round.exercise.forEach { exercise->
            playerExercise.playingExercise(exercise, pause, streamsWorkout)
        }
        speechManager.speech(round.speech.beforeEnd, pause, streamsWorkout)
        speechManager.speech(round.speech.afterEnd, pause, streamsWorkout)
    }
}