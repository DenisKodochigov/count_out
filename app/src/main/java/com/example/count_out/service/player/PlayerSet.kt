package com.example.count_out.service.player

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.Set
import com.example.count_out.service.stopwatch.delayMy
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class PlayerSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    suspend fun playingSet(sendToWS: SendToWorkService, sendToUI: SendToUI, ){
//        lg("playingSet ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToUI.set.value = sendToWS.getSet()
            speakSetBegin(set = sendToWS.getSet(), sendToUI = sendToUI)
            speakSetBody(sendToWS, sendToUI)
            speakSetRest(sendToWS, sendToUI)
            speakSetEnd(sendToWS, sendToUI)
            sendToUI.set.value = null
            sendToUI.nextSet.value = sendToWS.getNextSet()
        }
    }
    private suspend fun speakSetBegin(set: Set?, sendToUI: SendToUI){
        set?.let {
            it.speech.beforeStart.addMessage = textBeforeSet(it)
            speechManager.speech(sendToUI, it.speech.beforeStart)
            speechManager.speech(sendToUI, it.speech.afterStart)
        }
    }
    private suspend fun speakSetBody(sendToWS: SendToWorkService, sendToUI: SendToUI, ){
        when (sendToWS.getSet()?.goal){
            GoalSet.COUNT -> playSetCOUNT(sendToWS, sendToUI)
            GoalSet.DURATION -> playSetDURATION(sendToWS, sendToUI)
            GoalSet.DISTANCE -> playSetDISTANCE(sendToWS, sendToUI)
            GoalSet.COUNT_GROUP -> playSetCOUNTGROUP(sendToWS, sendToUI)
            null -> {}
        }
    }
    private suspend fun playSetDURATION(sendToWS: SendToWorkService, sendToUI: SendToUI){
        sendToWS.getSet()?.let{
            speakInterval( duration = (it.duration * 60).toInt(), sendToUI = sendToUI) }
    }
    private suspend fun playSetCOUNTGROUP(sendToWS: SendToWorkService, variablesOut: SendToUI){
        sendToWS.getSet()?.let {
            val listWordCount = it.groupCount.split(",")
            if ( listWordCount.isNotEmpty()){
                for (count in 0..< it.reps){
                    lg("Count group $count: ${listWordCount[count%listWordCount.size]}")
                    speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], variablesOut.runningState)
                    delayMy((sendToWS.getSetIntervalReps() * 1000).toLong(), variablesOut.runningState)
                }
            }
        }
    }
    private suspend fun playSetDISTANCE(sendToWS: SendToWorkService, sendToUI: SendToUI, ){}
    private suspend fun speakSetRest(sendToWS: SendToWorkService, sendToUI: SendToUI, ){
        sendToWS.getSet()?.let {
            if (it.timeRest > 0){
                CoroutineScope(Dispatchers.IO).launch {
                    speechManager.speech(sendToUI, SpeechDB( addMessage = it.timeRest.toString() + " "
                            + context.getString(R.string.rest_time)) )
                    speakNextExercise(sendToWS = sendToWS, sendToUI = sendToUI )
                }
                speakInterval( duration = it.timeRest, sendToUI = sendToUI)
            }
        }
    }
    private suspend fun playSetCOUNT(sendToWS: SendToWorkService, variablesOut: SendToUI, ){
        sendToWS.getSet()?.let {
            for (count in 1..it.reps){
                speechManager.speakOutFlush(text = count.toString(), variablesOut.runningState)
                delayMy((sendToWS.getSetIntervalReps() * 1000).toLong(), variablesOut.runningState)
            }
        }
    }
    private suspend fun speakInterval(duration: Int, sendToUI: SendToUI) {
        when (duration) {
            in 0..20 -> countDelay(duration = duration, divider = 5, sendToUI = sendToUI)
            in 20 .. 119 -> countDelay(duration = duration, divider = 10, sendToUI = sendToUI)
            else -> countDelay(duration = duration, divider = 60, sendToUI = sendToUI)
        }
    }

    private suspend fun speakSetEnd(sendToWS: SendToWorkService, sendToUI: SendToUI){
        if (sendToWS.lastSet()) {
            sendToWS.getExercise()?.speech?.beforeEnd?.let { speechManager.speech( sendToUI, it) }
            sendToWS.getExercise()?.speech?.afterEnd?.let { speechManager.speech( sendToUI, it) }
        } else {
            sendToWS.getSet()?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it) }
            sendToWS.getSet()?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
        }
    }
    private suspend fun speakNextExercise(sendToWS: SendToWorkService, sendToUI: SendToUI){
        if (sendToWS.lastSet()) {
            val nextExercise = sendToWS.getNextExercise()?.activity?.name ?: ""
            if (nextExercise != "") {
                speechManager.speech( sendToUI,
                    SpeechDB( message = context.getString(R.string.next_exercise) + " " + nextExercise))
            }
        }
    }
    private suspend fun countDelay(duration: Int, divider: Int, sendToUI: SendToUI){
        for ( count in 1..duration){
//            if (sendToUI.runningState.value == RunningState.Stopped) return
            if ( count % divider == 0 && !speechManager.getSpeeching())
                speechManager.speakOutFlush(text = count.toString(), sendToUI.runningState)
            delayMy(1000L, sendToUI.runningState)
        }
    }

    private fun textBeforeSet(set: Set): String{
        return when (set.goal){
            GoalSet.COUNT -> " " + getString(set.reps, R.plurals.repeat)
            GoalSet.COUNT_GROUP -> " " + getString(set.reps, R.plurals.repeat)
            GoalSet.DURATION ->
                if (set.duration < 1) " " + getString((set.duration * 60).roundToInt(), R.plurals.second)
                else getString(set.duration.roundToInt(), R.plurals.minutes)
            GoalSet.DISTANCE ->
                getString(set.distance.toInt(), R.plurals.km) +
                " " + getString((set.distance - (set.distance *1000)).roundToInt(), R.plurals.km)
        }
    }
    private fun getString(count: Double, id: Int): String {
        return context.resources.getQuantityString(id, count.toInt(), count)
    }
    private fun getString(count: Int, id: Int): String {
        return context.resources.getQuantityString(id, count, count)
    }
}