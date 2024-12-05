package com.example.count_out.service_count_out.work.execute

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TimeE
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

class ExecuteSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
{
    private val runningCountRest: MutableStateFlow<Boolean> = MutableStateFlow(true)
    var indexExercise = 0

    suspend fun executeSet(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        while (!runningCountRest.value) { delay(100L) }
        if (indexExercise != dataForWork.indexExercise) {
            dataForWork.setExecuteInfoExercise()
            indexExercise = dataForWork.indexExercise
        }
        dataForWork.getSet()?.let { currentSet->
            dataFromWork.countRest.value = 0
            dataFromWork.phaseWorkout.value = 1
            speakSetBegin(set = currentSet, dataFromWork = dataFromWork)
            speakSetBody(set = currentSet, dataForWork = dataForWork, dataFromWork = dataFromWork)
            speakSetEnd(currentSet, dataFromWork)
        }
    }
    private suspend fun speakSetBegin(set: Set, dataFromWork: DataFromWork){
        speechManager.speech(dataFromWork, SpeechDB(message = textBeforeSet(set)))
        speechManager.speech(dataFromWork, set.speech.beforeStart)
        speechManager.speech(dataFromWork, set.speech.afterStart)
    }
    private suspend fun speakSetBody(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork){
        when (set.goal){
            GoalSet.COUNT -> speakingCOUNT(set,dataForWork, dataFromWork)
            GoalSet.COUNT_GROUP -> speakingCOUNTGROUP(set, dataForWork, dataFromWork)
            GoalSet.DURATION -> speakingDURATION(set, dataFromWork)
            GoalSet.DISTANCE -> speakingDISTANCE(set, dataForWork, dataFromWork)
        }
    }
    private suspend fun speakingCOUNT(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork ){
        dataFromWork.enableChangeInterval.value = true
        for (count in 1..set.reps){
            dataFromWork.currentCount.value = count
            speechManager.speakOutFlush(text = count.toString(), dataFromWork)
            delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataFromWork.runningState)
        }
        dataFromWork.enableChangeInterval.value = false
    }
    private suspend fun speakingCOUNTGROUP(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork){
        val listWordCount = set.groupCount.split(",")
        if ( listWordCount.isNotEmpty()){
            for (count in 0..< set.reps){
                speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataFromWork)
                delayMy((dataForWork.getSetIntervalReps() * 1000).toLong(), dataFromWork.runningState)
            }
        }
    }
    private suspend fun speakingDURATION(set: Set, dataFromWork: DataFromWork){
            speakInterval( duration = (set.duration * 60), dataFromWork = dataFromWork)
    }
    private suspend fun speakingDISTANCE(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork ){}

    private suspend fun speakSetEnd(setCurrent: Set, dataFromWork: DataFromWork){
        speakingSetEnd(setCurrent, dataFromWork)
        CoroutineScope(Dispatchers.IO).launch { speakingSetRest(setCurrent, dataFromWork)}
        speechManager.speech(dataFromWork, SpeechDB( message = getStr(R.string.rest) + "  " +
             getPlurals(setCurrent.timeRest / (if (setCurrent.durationE == TimeE.MIN) 60 else 1),setCurrent.timeRestE.idS)
        ))
    }
    private suspend fun speakingSetRest(set: Set, dataFromWork: DataFromWork ){
        dataFromWork.phaseWorkout.value = 0
        if (set.timeRest > 0) {
            runningCountRest.value = false
            speakInterval(duration = set.timeRest, dataFromWork = dataFromWork)
            runningCountRest.value = true
        }
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
                speechManager.speakOutFlushBusy(text = count.toString(), dataFromWork)
            dataFromWork.countRest.value = if (!runningCountRest.value) count else 0
            delayMy(1000L, dataFromWork.runningState)
        }
    }

    private fun textBeforeSet(set: Set): String{
        return when (set.goal){
            GoalSet.COUNT -> " " + getPlurals(set.reps, R.plurals.repeat)
            GoalSet.COUNT_GROUP -> " " + getPlurals(set.reps, R.plurals.repeat)
            GoalSet.DURATION ->
                getPlurals(set.duration / (if (set.durationE == TimeE.MIN) 60 else 1), set.durationE.idS)
            GoalSet.DISTANCE ->
                getPlurals(set.duration / (if (set.distanceE == DistanceE.KM) 1000 else 1), set.distanceE.idS)
        }
    }
    private fun getPlurals(count: Int, id: Int): String {
        return context.resources.getQuantityString(id, count, count)
    }
    private fun getStr(id: Int) = context.getString(id)
}