package com.example.count_out.service_count_out.work.execute

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import com.example.count_out.entity.workout.Set
import com.example.count_out.service_count_out.stopwatch.delayMy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ExecuteSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    suspend fun executeSet(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        dataFromWork.speakingSet.value = dataForWork.getSet()
        speakSetBegin(set = dataForWork.getSet(), dataFromWork = dataFromWork)
        speakSetBody(dataForWork, dataFromWork)
        dataFromWork.speakingSet.value = null
        speakSetEnd(dataForWork, dataFromWork)
        dataFromWork.nextSet.value = dataForWork.getNextSet()
    }
    private suspend fun speakSetBegin(set: Set?, dataFromWork: DataFromWork){
        set?.let {
            speechManager.speech(dataFromWork, SpeechDB(message = textBeforeSet(it)))
            speechManager.speech(dataFromWork, it.speech.beforeStart)
            speechManager.speech(dataFromWork, it.speech.afterStart)
        }
    }
    private suspend fun speakSetBody(dataForWork: DataForWork, dataFromWork: DataFromWork){
        when (dataForWork.getSet()?.goal){
            GoalSet.COUNT -> speakingSetCOUNT(dataForWork, dataFromWork)
            GoalSet.DURATION -> speakingSetDURATION(dataForWork, dataFromWork)
            GoalSet.DISTANCE -> speakingSetDISTANCE(dataForWork, dataFromWork)
            GoalSet.COUNT_GROUP -> speakingSetCOUNTGROUP(dataForWork, dataFromWork)
            null -> {}
        }
    }
    private suspend fun speakingSetDURATION(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataForWork.getSet()?.let{
            speakInterval( duration = (it.duration * 60).toInt(), dataFromWork = dataFromWork) }
    }
    private suspend fun speakingSetCOUNTGROUP(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataForWork.getSet()?.let {
            val listWordCount = it.groupCount.split(",")
            if ( listWordCount.isNotEmpty()){
                for (count in 0..< it.reps){
                    speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataFromWork)
                    delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataFromWork.runningState)
                }
            }
        }
    }
    private suspend fun speakingSetDISTANCE(dataForWork: DataForWork, dataFromWork: DataFromWork ){}
    private suspend fun speakingSetCOUNT(dataForWork: DataForWork, dataFromWork: DataFromWork ){
        dataForWork.getSet()?.let {
            for (count in 1..it.reps){
                speechManager.speakOutFlush(text = count.toString(), dataFromWork)
                delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataFromWork.runningState)
            }
        }
    }

    private suspend fun speakSetEnd(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataForWork.getSet()?.let { set ->
            CoroutineScope(Dispatchers.IO).launch {
                speechManager.speech(dataFromWork, SpeechDB(message = "${set.timeRest} ${getStr(R.string.rest_time)}"))
                speakingSetEnd(dataForWork, dataFromWork)
                speakingNextExercise(dataForWork = dataForWork, dataFromWork = dataFromWork) }
            speakingSetRest(set, dataFromWork)
        }
    }
    private suspend fun speakingSetRest(set: Set, dataFromWork: DataFromWork ){
        speakInterval( duration = set.timeRest, dataFromWork = dataFromWork)
    }
    private suspend fun speakInterval(duration: Int, dataFromWork: DataFromWork) {
        when (duration) {
            in 0..20 -> countDelay(duration = duration, divider = 5, dataFromWork = dataFromWork)
            in 20 .. 119 -> countDelay(duration = duration, divider = 10, dataFromWork = dataFromWork)
            else -> countDelay(duration = duration, divider = 60, dataFromWork = dataFromWork)
        }
    }
    private suspend fun speakingSetEnd(dataForWork: DataForWork, dataFromWork: DataFromWork){
        if (dataForWork.lastSet()) {
            dataForWork.getExercise()?.speech?.beforeEnd?.let { speechManager.speech( dataFromWork, it) }
            dataForWork.getExercise()?.speech?.afterEnd?.let { speechManager.speech( dataFromWork, it) }
        } else {
            dataForWork.getSet()?.speech?.beforeEnd?.let { speechManager.speech(dataFromWork, it) }
            dataForWork.getSet()?.speech?.afterEnd?.let { speechManager.speech(dataFromWork, it) }
        }
    }
    private suspend fun speakingNextExercise(dataForWork: DataForWork, dataFromWork: DataFromWork){
        if (dataForWork.lastSet()) {
            dataForWork.getNextExercise()?.activity?.let { activity ->
                val desc = if (dataForWork.enableSpeechDescription.value) activity.description else ""
                speechManager.speech(dataFromWork,
                    SpeechDB(message = "${getStr(R.string.next_exercise)} ${activity.name}. $desc"))
            }
        }
    }

    private suspend fun countDelay(duration: Int, divider: Int, dataFromWork: DataFromWork){
        for ( count in 1..duration){
            if (dataFromWork.runningState.value == RunningState.Stopped) return
            if ( count % divider == 0 && !speechManager.getSpeeching())
                speechManager.speakOutFlush(text = count.toString(), dataFromWork)
            delayMy(1000L, dataFromWork.runningState)
        }
    }

    private fun textBeforeSet(set: Set): String{
        return when (set.goal){
            GoalSet.COUNT -> " " + getPlurals(set.reps, R.plurals.repeat)
            GoalSet.COUNT_GROUP -> " " + getPlurals(set.reps, R.plurals.repeat)
            GoalSet.DURATION ->
                if (set.duration < 1) " " + getPlurals((set.duration * 60).roundToInt(), R.plurals.second)
                else getPlurals(set.duration.roundToInt(), R.plurals.minutes)
            GoalSet.DISTANCE ->
                getPlurals(set.distance.toInt(), R.plurals.km) +
                " " + getPlurals((set.distance - (set.distance *1000)).roundToInt(), R.plurals.km)
        }
    }
    private fun getPlurals(count: Int, id: Int): String {
        return context.resources.getQuantityString(id, count, count)
    }
    private fun getStr(id: Int) = context.getString(id)
}