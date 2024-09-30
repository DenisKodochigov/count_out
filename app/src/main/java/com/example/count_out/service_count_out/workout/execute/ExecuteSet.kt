package com.example.count_out.service_count_out.workout.execute

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.Set
import com.example.count_out.service_count_out.stopwatch.delayMy
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ExecuteSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    suspend fun executeSet(sendToSrv: SendToService, sendToUI: SendToUI, ){
//        lg("playingSet ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToUI.set.value = sendToSrv.getSet()
            speakSetBegin(set = sendToSrv.getSet(), sendToUI = sendToUI)
            speakSetBody(sendToSrv, sendToUI)
            speakSetRest(sendToSrv, sendToUI)
            speakSetEnd(sendToSrv, sendToUI)
            sendToUI.set.value = null
            sendToUI.nextSet.value = sendToSrv.getNextSet()
        }
    }
    private suspend fun speakSetBegin(set: Set?, sendToUI: SendToUI){
        set?.let {
            it.speech.beforeStart.addMessage = textBeforeSet(it)
            speechManager.speech(sendToUI, it.speech.beforeStart)
            speechManager.speech(sendToUI, it.speech.afterStart)
        }
    }
    private suspend fun speakSetBody(sendToSrv: SendToService, sendToUI: SendToUI, ){
        sendToUI.mark.value = sendToUI.mark.value.copy(set = 1)
        when (sendToSrv.getSet()?.goal){
            GoalSet.COUNT -> playSetCOUNT(sendToSrv, sendToUI)
            GoalSet.DURATION -> playSetDURATION(sendToSrv, sendToUI)
            GoalSet.DISTANCE -> playSetDISTANCE(sendToSrv, sendToUI)
            GoalSet.COUNT_GROUP -> playSetCOUNTGROUP(sendToSrv, sendToUI)
            null -> {}
        }
        sendToUI.mark.value = sendToUI.mark.value.copy(set = 0)
    }
    private suspend fun playSetDURATION(sendToSrv: SendToService, sendToUI: SendToUI){
        sendToSrv.getSet()?.let{
            speakInterval( duration = (it.duration * 60).toInt(), sendToUI = sendToUI) }
    }
    private suspend fun playSetCOUNTGROUP(sendToSrv: SendToService, variablesOut: SendToUI){
        sendToSrv.getSet()?.let {
            val listWordCount = it.groupCount.split(",")
            if ( listWordCount.isNotEmpty()){
                for (count in 0..< it.reps){
                    lg("Count group $count: ${listWordCount[count%listWordCount.size]}")
                    speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], variablesOut.runningState)
                    delayMy((sendToSrv.getSetIntervalReps() * 1000).toLong(), variablesOut.runningState)
                }
            }
        }
    }
    private suspend fun playSetDISTANCE(sendToSrv: SendToService, sendToUI: SendToUI, ){}
    private suspend fun speakSetRest(sendToSrv: SendToService, sendToUI: SendToUI, ){
        sendToSrv.getSet()?.let {
            if (it.timeRest > 0){
                sendToUI.mark.value = sendToUI.mark.value.copy(rest = 1)
                CoroutineScope(Dispatchers.IO).launch {
                    speechManager.speech(sendToUI, SpeechDB( addMessage = it.timeRest.toString() + " "
                            + context.getString(R.string.rest_time)) )
                    speakNextExercise(sendToSrv = sendToSrv, sendToUI = sendToUI )
                }
                speakInterval( duration = it.timeRest, sendToUI = sendToUI)
                sendToUI.mark.value = sendToUI.mark.value.copy(rest = 0)
            }
        }
    }
    private suspend fun playSetCOUNT(sendToSrv: SendToService, variablesOut: SendToUI, ){
        sendToSrv.getSet()?.let {
            for (count in 1..it.reps){
                speechManager.speakOutFlush(text = count.toString(), variablesOut.runningState)
                delayMy((sendToSrv.getSetIntervalReps() * 1000).toLong(), variablesOut.runningState)
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

    private suspend fun speakSetEnd(sendToSrv: SendToService, sendToUI: SendToUI){
        if (sendToSrv.lastSet()) {
            sendToSrv.getExercise()?.speech?.beforeEnd?.let { speechManager.speech( sendToUI, it) }
            sendToSrv.getExercise()?.speech?.afterEnd?.let { speechManager.speech( sendToUI, it) }
        } else {
            sendToSrv.getSet()?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it) }
            sendToSrv.getSet()?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
        }
    }
    private suspend fun speakNextExercise(sendToSrv: SendToService, sendToUI: SendToUI){
        if (sendToSrv.lastSet()) {
            val nextExercise = sendToSrv.getNextExercise()?.activity?.name ?: ""
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