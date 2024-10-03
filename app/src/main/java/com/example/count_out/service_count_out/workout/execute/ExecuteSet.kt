package com.example.count_out.service_count_out.workout.execute

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.workout.Set
import com.example.count_out.service_count_out.stopwatch.delayMy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ExecuteSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    suspend fun executeSet(dataForWork: DataForServ, dataForUI: DataForUI ){
        if (dataForUI.runningState.value == RunningState.Stopped) dataForUI.cancelCoroutineWork()
        dataForUI.set.value = dataForWork.getSet()
        speakSetBegin(set = dataForWork.getSet(), dataForUI = dataForUI)
        speakSetBody(dataForWork, dataForUI)
        speakSetRest(dataForWork, dataForUI)
        speakSetEnd(dataForWork, dataForUI)
        dataForUI.set.value = null
        dataForUI.nextSet.value = dataForWork.getNextSet()
    }
    private suspend fun speakSetBegin(set: Set?, dataForUI: DataForUI){
        set?.let {
            it.speech.beforeStart.addMessage = textBeforeSet(it)
            speechManager.speech(dataForUI, it.speech.beforeStart)
            speechManager.speech(dataForUI, it.speech.afterStart)
        }
    }
    private suspend fun speakSetBody(dataForWork: DataForServ, dataForUI: DataForUI){
        dataForUI.mark.value = dataForUI.mark.value.copy(set = 1)
        when (dataForWork.getSet()?.goal){
            GoalSet.COUNT -> speakingSetCOUNT(dataForWork, dataForUI)
            GoalSet.DURATION -> speakingSetDURATION(dataForWork, dataForUI)
            GoalSet.DISTANCE -> speakingSetDISTANCE(dataForWork, dataForUI)
            GoalSet.COUNT_GROUP -> speakingSetCOUNTGROUP(dataForWork, dataForUI)
            null -> {}
        }
        dataForUI.mark.value = dataForUI.mark.value.copy(set = 0)
    }
    private suspend fun speakingSetDURATION(dataForWork: DataForServ, dataForUI: DataForUI){
        dataForWork.getSet()?.let{
            speakInterval( duration = (it.duration * 60).toInt(), dataForUI = dataForUI) }
    }
    private suspend fun speakingSetCOUNTGROUP(dataForWork: DataForServ, dataForUI: DataForUI){
        dataForWork.getSet()?.let {
            val listWordCount = it.groupCount.split(",")
            if ( listWordCount.isNotEmpty()){
                for (count in 0..< it.reps){
//                    lg("Count group $count: ${listWordCount[count%listWordCount.size]}")
                    speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataForUI)
                    delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataForUI.runningState)
                }
            }
        }
    }
    private suspend fun speakingSetDISTANCE(dataForWork: DataForServ, dataForUI: DataForUI ){}
    private suspend fun speakSetRest(dataForWork: DataForServ, dataForUI: DataForUI ){
        dataForWork.getSet()?.let {
            if (it.timeRest > 0){
                dataForUI.mark.value = dataForUI.mark.value.copy(rest = 1)
                CoroutineScope(Dispatchers.IO).launch {
                    speechManager.speech(dataForUI, SpeechDB( addMessage = it.timeRest.toString() + " "
                            + context.getString(R.string.rest_time)) )
                    speakNextExercise(dataForWork = dataForWork, dataForUI = dataForUI )
                }
                speakInterval( duration = it.timeRest, dataForUI = dataForUI)
                dataForUI.mark.value = dataForUI.mark.value.copy(rest = 0)
            }
        }
    }
    private suspend fun speakingSetCOUNT(dataForWork: DataForServ, dataForUI: DataForUI ){
        dataForWork.getSet()?.let {
            for (count in 1..it.reps){
                speechManager.speakOutFlush(text = count.toString(), dataForUI)
                delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataForUI.runningState)
            }
        }
    }
    private suspend fun speakInterval(duration: Int, dataForUI: DataForUI) {
        when (duration) {
            in 0..20 -> countDelay(duration = duration, divider = 5, dataForUI = dataForUI)
            in 20 .. 119 -> countDelay(duration = duration, divider = 10, dataForUI = dataForUI)
            else -> countDelay(duration = duration, divider = 60, dataForUI = dataForUI)
        }
    }

    private suspend fun speakSetEnd(dataForWork: DataForServ, dataForUI: DataForUI){
        if (dataForWork.lastSet()) {
            dataForWork.getExercise()?.speech?.beforeEnd?.let { speechManager.speech( dataForUI, it) }
            dataForWork.getExercise()?.speech?.afterEnd?.let { speechManager.speech( dataForUI, it) }
        } else {
            dataForWork.getSet()?.speech?.beforeEnd?.let { speechManager.speech(dataForUI, it) }
            dataForWork.getSet()?.speech?.afterEnd?.let { speechManager.speech(dataForUI, it) }
        }
    }
    private suspend fun speakNextExercise(dataForWork: DataForServ, dataForUI: DataForUI){
        if (dataForWork.lastSet()) {
            val nextExercise = dataForWork.getNextExercise()?.activity?.name ?: ""
            if (nextExercise != "") {
                speechManager.speech( dataForUI,
                    SpeechDB( message = context.getString(R.string.next_exercise) + " " + nextExercise))
            }
        }
    }
    private suspend fun countDelay(duration: Int, divider: Int, dataForUI: DataForUI){
        for ( count in 1..duration){
//            if (sendToUI.runningState.value == RunningState.Stopped) return
            if ( count % divider == 0 && !speechManager.getSpeeching())
                speechManager.speakOutFlush(text = count.toString(), dataForUI)
            delayMy(1000L, dataForUI.runningState)
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
    private fun getString(count: Int, id: Int): String {
        return context.resources.getQuantityString(id, count, count)
    }
}