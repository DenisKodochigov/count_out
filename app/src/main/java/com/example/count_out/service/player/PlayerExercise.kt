package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.StreamsWorkout
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, private val playerSet: PlayerSet)
{
    private val show = false
    suspend fun playingExercise(
        exercise: Exercise,
        pause: MutableState<Boolean>, streamsWorkout: StreamsWorkout
    ){
        speechManager.speech(exercise.speech.beforeStart + " " + exercise.activity.name, pause, streamsWorkout)
        speechManager.speech(exercise.activity.description, pause, streamsWorkout)
        speechManager.speech(exercise.speech.afterStart, pause, streamsWorkout)
        exercise.sets.forEach { set->
            playerSet.playingSet(set, pause, streamsWorkout)
        }
        speechManager.speech(exercise.speech.beforeEnd, pause, streamsWorkout)
        speechManager.speech(exercise.speech.afterEnd, pause, streamsWorkout)
    }
}