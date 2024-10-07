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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ExecuteSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    private val countRest: MutableStateFlow<Boolean> = MutableStateFlow(true)

    suspend fun executeSet(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        while (!countRest.value) {delay(100L)}
        dataForWork.getSet()?.let { setCurrent->
            dataFromWork.speakingSet.value = setCurrent
            speakSetBegin(set = setCurrent, dataFromWork = dataFromWork)
            speakSetBody(set = setCurrent, dataForWork = dataForWork, dataFromWork = dataFromWork)
            dataFromWork.speakingSet.value = null
            dataFromWork.nextSet.value = dataForWork.getNextSet()
            speakSetEnd(setCurrent, dataFromWork)
        }
    }
    private suspend fun speakSetBegin(set: Set, dataFromWork: DataFromWork){
        speechManager.speech(dataFromWork, SpeechDB(message = textBeforeSet(set)))
        speechManager.speech(dataFromWork, set.speech.beforeStart)
        speechManager.speech(dataFromWork, set.speech.afterStart)
    }
    private suspend fun speakSetBody(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork){
        when (set.goal){
            GoalSet.COUNT -> speakingSetCOUNT(set,dataForWork, dataFromWork)
            GoalSet.DURATION -> speakingSetDURATION(set, dataFromWork)
            GoalSet.DISTANCE -> speakingSetDISTANCE(set, dataForWork, dataFromWork)
            GoalSet.COUNT_GROUP -> speakingSetCOUNTGROUP(set, dataForWork, dataFromWork)
        }
    }
    private suspend fun speakingSetDURATION(set: Set, dataFromWork: DataFromWork){
            speakInterval( duration = (set.duration * 60).toInt(), dataFromWork = dataFromWork)
    }
    private suspend fun speakingSetCOUNTGROUP(set: Set, dataForWork: DataForWork,dataFromWork: DataFromWork){
        val listWordCount = set.groupCount.split(",")
        if ( listWordCount.isNotEmpty()){
            for (count in 0..< set.reps){
                speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataFromWork)
                delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataFromWork.runningState)
            }
        }
    }
    private suspend fun speakingSetDISTANCE(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork ){}
    private suspend fun speakingSetCOUNT(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork ){
        for (count in 1..set.reps){
            speechManager.speakOutFlush(text = count.toString(), dataFromWork)
            delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataFromWork.runningState)
        }
    }

    private suspend fun speakSetEnd(setCurrent: Set, dataFromWork: DataFromWork){
        speakingSetEnd(setCurrent, dataFromWork)
        CoroutineScope(Dispatchers.IO).launch { speakingSetRest(setCurrent, dataFromWork)}
        speechManager.speech(dataFromWork, SpeechDB( message =
            "${setCurrent.timeRest} ${getStr(R.string.rest_time)}"))
    }
    private suspend fun speakingSetRest(set: Set, dataFromWork: DataFromWork ){
        countRest.value = false
        speakInterval( duration = set.timeRest, dataFromWork = dataFromWork)
        countRest.value = true
    }
    private suspend fun speakInterval(duration: Int, dataFromWork: DataFromWork) {
        when (duration) {
            in 0..20 -> countDelay(duration = duration, divider = 5, dataFromWork = dataFromWork)
            in 20 .. 119 -> countDelay(duration = duration, divider = 10, dataFromWork = dataFromWork)
            else -> countDelay(duration = duration, divider = 60, dataFromWork = dataFromWork)
        }
    }
    private suspend fun speakingSetEnd(setCurrent: Set, dataFromWork: DataFromWork){
        speechManager.speech(dataFromWork, setCurrent.speech.beforeEnd)
        speechManager.speech(dataFromWork, setCurrent.speech.afterEnd)
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