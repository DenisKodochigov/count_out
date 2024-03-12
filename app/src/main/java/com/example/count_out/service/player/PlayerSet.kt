package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Set
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager)
{
    @Inject lateinit var playerExercise: PlayerExercise
    private val show = false
    suspend fun playingSet(
        set: Set,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ){
        val textBeforeSet = if (set.reps > 0) {
            " " + set.reps.toString() + " повторений"
        } else if (set.duration > 0){
            if (set.duration < 1) " " + (set.duration * 60).toInt().toString() + " секунд"
            else if (set.duration == 1.0) " " + set.duration .toString() + " минута"
            else if (set.duration > 1.0 && set.duration < 5.0) " " + set.duration .toString() + " минуты"
            else " " + set.duration .toString() + " минут"
        } else ""

        speechManager.speech(set.speech.beforeStart + textBeforeSet, pause, flowStateServiceMutable)
        speechManager.speech(set.speech.afterStart, pause, flowStateServiceMutable)

        if (set.reps > 0) {
            for (count in 1..set.reps){
                speechManager.speakOutFlush(text = count.toString())
                log(show, "PlayerSet count: $count")
                delay((set.intervalReps * 1000).toLong())
            }
        } else if (set.duration > 0){
            delay((set.duration * 60 * 1000).toLong())
        }
        if (set.timeRest > 0) {
            speechManager.speech(set.timeRest.toString() + " секунд отдыха.", pause, flowStateServiceMutable)
            delay((set.timeRest * 1000).toLong())
        }
        speechManager.speech(set.speech.beforeEnd, pause, flowStateServiceMutable)
        speechManager.speech(set.speech.afterEnd, pause, flowStateServiceMutable)
    }
//    suspend fun playingSet(
//        set: Set,
//        pause: MutableState<Boolean>, streamsWorkout: StreamsWorkout
//    ){
//        val textBeforeSet = if (set.reps > 0) {
//            " " + set.reps.toString() + " повторений"
//        } else if (set.duration > 0){
//            if (set.duration < 1) " " + (set.duration * 60).toInt().toString() + " секунд"
//            else if (set.duration == 1.0) " " + set.duration .toString() + " минута"
//            else if (set.duration > 1.0 && set.duration < 5.0) " " + set.duration .toString() + " минуты"
//            else " " + set.duration .toString() + " минут"
//        } else ""
//
//        speechManager.speech(set.speech.beforeStart + textBeforeSet, pause, streamsWorkout)
//        speechManager.speech(set.speech.afterStart, pause, streamsWorkout)
//
//        if (set.reps > 0) {
//            for (count in 1..set.reps){
//                speechManager.speakOutFlush(text = count.toString())
//                log(show, "PlayerSet count: $count")
//                delay((set.intervalReps * 1000).toLong())
//            }
//        } else if (set.duration > 0){
//            delay((set.duration * 60 * 1000).toLong())
//        }
//        if (set.timeRest > 0) {
//            speechManager.speech(set.timeRest.toString() + " секунд отдыха.", pause, streamsWorkout)
//            delay((set.timeRest * 1000).toLong())
//        }
//        speechManager.speech(set.speech.beforeEnd, pause, streamsWorkout)
//        speechManager.speech(set.speech.afterEnd, pause, streamsWorkout)
//    }
}