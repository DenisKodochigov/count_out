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
import com.example.count_out.entity.speech.SpeechKit
import com.example.count_out.entity.workout.Set
import com.example.count_out.service_count_out.stopwatch.delayMy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RunWorkOut @Inject constructor( val speechManager:SpeechManager, val context: Context) {

    private val runningCountRest: MutableStateFlow<Boolean> = MutableStateFlow(true)

    suspend fun runWorkOut(dataForWork: DataForWork, dataFromWork: DataFromWork){

        var currentIndRound = 0
        var currentIndExercise = 0

        dataForWork.training.value?.let { tr->
            tr.speech.beforeStart.addMessage = tr.name
            speechStart(dataFromWork, tr.speech)
            dataForWork.createMapTraining()

            dataForWork.map.forEachIndexed { indM, item->
                if ( currentIndRound != item.roundCurrent )
                    item.round?.speech?.let { speechStart(dataFromWork, it)}
                if ( currentIndExercise != item.exerciseNumber ) {
                    item.exercise?.let { exerciseLet->
                        speechStart(dataFromWork, exerciseLet.speech)
                        val desc = if (dataForWork.enableSpeechDescription.value) exerciseLet.activity.description else ""
                        speechManager.speech(dataFromWork, SpeechDB(message =
                        "${context.getString(R.string.next_exercise)} ${exerciseLet.activity.name}. $desc"))
                    }
                    dataForWork.setExecuteInfoExercise(index = indM)
                }
                dataForWork.setExecuteInfoSet(indM)
                executeSet( item.set, dataFromWork )
                if ( currentIndExercise != item.exerciseNumber )
                    item.exercise?.speech?.let { speechEnd(dataFromWork, it)}
                if ( currentIndRound != item.roundCurrent )
                    item.round?.speech?.let { speechEnd(dataFromWork, it)}
                currentIndExercise = item.exerciseNumber
                currentIndRound = item.roundCurrent
            }
            speechEnd(dataFromWork, tr.speech)
            dataFromWork.runningState.value = RunningState.Stopped
        }
    }

    private suspend fun executeSet(set: Set?, dataFromWork: DataFromWork){
        dataFromWork.trap()
        while (!runningCountRest.value) { delay(100L) }
        set?.let { currentSet->
            dataFromWork.countRest.value = 0
            dataFromWork.phaseWorkout.value = 1
            speakSetBegin(set = currentSet, dataFromWork = dataFromWork)
            speakSetBody(set = currentSet, dataFromWork = dataFromWork)
            speakEnd(currentSet, dataFromWork)
        }
    }
    private suspend fun speakSetBegin(set: Set, dataFromWork: DataFromWork){
        speechManager.speech(dataFromWork, SpeechDB(message = textBeforeSet(set)))
        speechStart(dataFromWork, set.speech)
    }
    private suspend fun speakSetBody(set: Set, dataFromWork: DataFromWork){
        when (set.goal){
            GoalSet.COUNT -> speakingCOUNT(set, dataFromWork)
            GoalSet.COUNT_GROUP -> speakingCOUNTGROUP(set, dataFromWork)
            GoalSet.DURATION -> speakingDURATION(set, dataFromWork)
            GoalSet.DISTANCE -> speakingDISTANCE(set, dataFromWork)
        }
    }
    private suspend fun speakingCOUNT(set: Set, dataFromWork: DataFromWork ){
        dataFromWork.enableChangeInterval.value = true
        for (count in 1..set.reps){
            dataFromWork.currentCount.value = count
            speechManager.speakOutFlush(text = count.toString(), dataFromWork)
            delayMy((set.intervalReps * 1000).toLong(), dataFromWork.runningState)
        }
        dataFromWork.enableChangeInterval.value = false
    }
    private suspend fun speakingCOUNTGROUP(set: Set, dataFromWork: DataFromWork){
        val listWordCount = set.groupCount.split(",")
        if ( listWordCount.isNotEmpty()){
            for (count in 0..< set.reps){
                speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataFromWork)
                delayMy((set.intervalReps * 1000).toLong(), dataFromWork.runningState)
            }
        }
    }
    private suspend fun speakingDURATION(set: Set, dataFromWork: DataFromWork){
        speakInterval( duration = (set.duration / (if (set.durationE == TimeE.MIN) 60 else 1)),
            dataFromWork = dataFromWork)
    }
    private fun speakingDISTANCE(set: Set, dataFromWork: DataFromWork ){}

    private suspend fun speakEnd(setCurrent: Set, dataFromWork: DataFromWork){
        speechEnd(dataFromWork, setCurrent.speech)
        CoroutineScope(Dispatchers.IO).launch { speakingRest(setCurrent, dataFromWork)}
        speechManager.speech(dataFromWork, SpeechDB( message = getStr(R.string.rest) + "  " +
                getPlurals(setCurrent.timeRest / (if (setCurrent.durationE == TimeE.MIN) 60 else 1),setCurrent.timeRestE.idS)
        ))
    }
    private suspend fun speakingRest(set: Set, dataFromWork: DataFromWork ){
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
    private suspend fun countDelay(duration: Int, divider: Int, dataFromWork: DataFromWork){
        for ( count in 1..duration){
            if (dataFromWork.runningState.value == RunningState.Stopped) return
            if ( count % divider == 0 && !speechManager.getSpeeching())
                speechManager.speakOutFlushBusy(text = count.toString(), dataFromWork)
            dataFromWork.currentDuration.value = if (runningCountRest.value) count else 0
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
    fun trap(){}
    private suspend fun speechStart(dataFromWork: DataFromWork, speech: SpeechKit){
        speechManager.speech(dataFromWork, speech.beforeStart)
        speechManager.speech(dataFromWork, speech.afterStart)
    }
    private suspend fun speechEnd(dataFromWork: DataFromWork, speech: SpeechKit){
        speechManager.speech(dataFromWork, speech.beforeEnd)
        speechManager.speech(dataFromWork, speech.afterEnd)
    }
}