package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.StateWorkOut
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, private val playerSet: PlayerSet)
{
    suspend fun playingExercise(
        exercise: Exercise,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ){
        speechManager.speech(exercise.speech.beforeStart + " " + exercise.activity.name, pause, flowStateServiceMutable)
        speechManager.speech(exercise.activity.description, pause, flowStateServiceMutable)
        speechManager.speech(exercise.speech.afterStart, pause, flowStateServiceMutable)
        exercise.sets.forEach { set->
            playerSet.playingSet(set, pause, flowStateServiceMutable)
        }
        speechManager.speech(exercise.speech.beforeEnd, pause, flowStateServiceMutable)
        speechManager.speech(exercise.speech.afterEnd, pause, flowStateServiceMutable)
    }
//    suspend fun playingExercise(
//        exercise: Exercise,
//        pause: MutableState<Boolean>, streamsWorkout: StreamsWorkout
//    ){
//        speechManager.speech(exercise.speech.beforeStart + " " + exercise.activity.name, pause, streamsWorkout)
//        speechManager.speech(exercise.activity.description, pause, streamsWorkout)
//        speechManager.speech(exercise.speech.afterStart, pause, streamsWorkout)
//        exercise.sets.forEach { set->
//            playerSet.playingSet(set, pause, streamsWorkout)
//        }
//        speechManager.speech(exercise.speech.beforeEnd, pause, streamsWorkout)
//        speechManager.speech(exercise.speech.afterEnd, pause, streamsWorkout)
//    }
}