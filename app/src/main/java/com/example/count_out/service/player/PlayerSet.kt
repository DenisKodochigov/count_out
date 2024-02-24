package com.example.count_out.service.player

import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Set
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager)
{
    @Inject lateinit var playerExercise: PlayerExercise
    private val show = false
    suspend fun playingSet(set: Set, stateService: MutableStateFlow<StateWorkOutDB>){
        val textBeforeSet = if (set.reps > 0) {
            " " + set.reps.toString() + " повторений"
        } else if (set.duration > 0){
            if (set.duration < 1) " " + (set.duration * 60).toInt().toString() + " секунд"
            else if (set.duration == 1.0) " " + set.duration .toString() + " минута"
            else if (set.duration > 1.0 && set.duration < 5.0) " " + set.duration .toString() + " минуты"
            else " " + set.duration .toString() + " минут"
        } else ""

        speech(set.speech.beforeStart + textBeforeSet, stateService)
        speech(set.speech.afterStart, stateService)

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
            speech(set.timeRest.toString() + " секунд отдыха.", stateService)
            delay((set.timeRest * 1000).toLong())
        }
        speech(set.speech.beforeEnd, stateService)
        speech(set.speech.afterEnd, stateService)
    }
    suspend fun speech(text: String, stateService: MutableStateFlow<StateWorkOutDB>){
        if (text.length > 1) {
            stateService.value = StateWorkOutDB(state = text)
            log(show, "PlayerSet stateService: ${stateService.value}")
            speechManager.speakOut(text, 0L)
        }
    }
}