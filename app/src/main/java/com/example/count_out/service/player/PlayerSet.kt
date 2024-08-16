package com.example.count_out.service.player

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.Set
import com.example.count_out.entity.StateRunning
import com.example.count_out.helpers.delayMy
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    suspend fun playingSet(template: SendToWorkService, sendToUI: SendToUI, ){
        if (sendToUI.stateRunning.value == StateRunning.Started) {
            sendToUI.set.value = template.getSet()
            template.getSet()?.let { it.speech.beforeStart.addMessage = textBeforeSet(it)}
            template.getSet()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            template.getSet()?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }

            bodyPlayingSet(template, sendToUI)
            sendToUI.set.value = null
            playRest(template, sendToUI)
        }
    }
    private suspend fun bodyPlayingSet(template: SendToWorkService, sendToUI: SendToUI, ){
        when (template.getSet()?.goal){
            GoalSet.COUNT -> playSetCOUNT(template, sendToUI)
            GoalSet.DURATION -> playSetDURATION(template, sendToUI)
            GoalSet.DISTANCE -> playSetDISTANCE(template, sendToUI)
            GoalSet.COUNT_GROUP -> playSetCOUNTGROUP(template, sendToUI)
            null -> {}
        }
    }
    private suspend fun playRest(template: SendToWorkService, sendToUI: SendToUI, ){
        val job = CoroutineScope(Dispatchers.IO).launch {
            playRestNextExercise(template = template, sendToUI = sendToUI) }
        delayAndPlayEndSet(template = template, sendToUI = sendToUI)
//        job.cancelAndJoin()
    }

    private suspend fun playSetCOUNT(template: SendToWorkService, variablesOut: SendToUI, ){
        template.getSet()?.let {
            for (count in 1..it.reps){
                speechManager.speakOutFlush(text = count.toString(), variablesOut.stateRunning)
                lg("playSetCOUNT ${template.getSetIntervalReps()}")
                delayMy((template.getSetIntervalReps() * 1000).toLong(), variablesOut.stateRunning)
            }
        }
    }
    private suspend fun playSetDURATION(template: SendToWorkService, sendToUI: SendToUI){
        template.getSet()?.let{
            playDelay( duration = (it.duration * 60).toInt(), sendToUI = sendToUI) }
    }
    private suspend fun playSetCOUNTGROUP(template: SendToWorkService, variablesOut: SendToUI){
        template.getSet()?.let {
            val listWordCount = it.groupCount.split(",")
            if ( listWordCount.isNotEmpty()){
                for (count in 0..< it.reps){
                    lg("Count group $count: ${listWordCount[count%listWordCount.size]}")
                    speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], variablesOut.stateRunning)
                    delayMy((template.getSetIntervalReps() * 1000).toLong(), variablesOut.stateRunning)
                }
            }
        }

    }
    private suspend fun playSetDISTANCE(template: SendToWorkService, sendToUI: SendToUI, ){}

    private suspend fun delayAndPlayEndSet(template: SendToWorkService, sendToUI: SendToUI){
        template.getSet()?.let {
            if (it.timeRest > 0) playDelay( duration = it.timeRest, sendToUI = sendToUI)
            playEndSet(template = template, sendToUI = sendToUI)
        }
    }
    private suspend fun playDelay(duration: Int, sendToUI: SendToUI){
        if (duration < 20) {
            countDelay(duration = duration, divider = 5, sendToUI = sendToUI) }
        else if (duration in 20..119) {
            countDelay(duration = duration, divider = 10, sendToUI = sendToUI) }
        else { countDelay(duration = duration, divider = 60, sendToUI = sendToUI) }
    }
    private suspend fun playRestNextExercise(template: SendToWorkService, sendToUI: SendToUI ){
        template.getSet()?.let {
            if (it.timeRest > 0){
                speechManager.speech(sendToUI, SpeechDB( addMessage = it.timeRest.toString() + " " + context.getString(R.string.rest_time)) )
                nextExerciseSpeech(template = template, variablesOut = sendToUI )
            }
        }
    }
    private suspend fun playEndSet(template: SendToWorkService, sendToUI: SendToUI){
        if (template.lastSet()) {
            template.getExercise()?.speech?.beforeEnd?.let { speechManager.speech( sendToUI, it) }
            template.getExercise()?.speech?.afterEnd?.let { speechManager.speech( sendToUI, it) }
        } else {
            template.getSet()?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it) }
            template.getSet()?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
        }
    }
    private suspend fun nextExerciseSpeech(template: SendToWorkService, variablesOut: SendToUI){
        if (template.lastSet()) {
            val nextExercise = template.getNextExercise()?.activity?.name ?: ""
            val message = if (nextExercise != "") {
                context.getString(R.string.next_exercise) + " " + nextExercise } else ""
            speechManager.speech(variablesOut, SpeechDB( message = message ) )
        }
    }
    private suspend fun countDelay(duration: Int, divider: Int, sendToUI: SendToUI){
        for ( count in 1..duration){
            if ( count % divider == 0 && !speechManager.getSpeeching())
                speechManager.speakOutFlush(text = count.toString(), sendToUI.stateRunning)
            delayMy(1000L, sendToUI.stateRunning)
        }
    }
    private fun textBeforeSet(set: Set): String{
        return when (set.goal){
            GoalSet.COUNT -> " " + set.reps.toString() + " повторений"
            GoalSet.COUNT_GROUP -> " " + set.reps.toString() + " повторений"
            GoalSet.DURATION ->
                if (set.duration < 1) " " + (set.duration * 60).toInt().toString() + " секунд"
                else if (set.duration == 1.0) " " + set.duration .toString() + " минута"
                else if (set.duration > 1.0 && set.duration < 5.0) " " +
                        set.duration .toString() + " минуты"
                else " " + set.duration .toString() + " минут"
            GoalSet.DISTANCE ->
                if (set.distance < 5.0) " " + set.distance .toString() + " километра"
                else " " + set.distance .toString() + " километров"
        }
    }
}