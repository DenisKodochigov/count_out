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
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

class RunWorkOut @Inject constructor( val speechManager:SpeechManager, val context: Context) {

    private val runningCountRest: MutableStateFlow<Boolean> = MutableStateFlow(true)
//    val runningCountRest = Object()

    suspend fun runWorkOut(dataForWork: DataForWork, dataFromWork: DataFromWork){

        var currentIndRound = 0L
        var currentIndExercise = 0L

        dataForWork.training.value?.let { tr->
            tr.speech.beforeStart.addMessage = tr.name
            speechStart(dataFromWork, tr.speech)
            dataForWork.map.forEachIndexed { indM, item->
                dataForWork.indexMap = indM
                if ( currentIndRound != item.round?.idRound ) {
                    dataForWork.sendStepTrainingShort()
                    item.round?.speech?.let { speechStart(dataFromWork, it) }
                }
                if ( currentIndExercise != item.exercise?.idExercise ) {
                    dataForWork.sendStepTrainingShort()
                    item.exercise?.let { exerciseLet->
                        speechStart(dataFromWork, exerciseLet.speech)
                        val desc = if (dataForWork.enableSpeechDescription.value) exerciseLet.activity.description else ""
                        speechManager.speech(dataFromWork, SpeechDB(message =
                        "${context.getString(R.string.next_exercise)} ${exerciseLet.activity.name}. $desc"))
                    }
//                    dataForWork.setExecuteInfoExercise(index = indM)
                }
                executeSet( item.currentSet, dataForWork, dataFromWork )
                if ( currentIndExercise != item.exercise?.idExercise )
                    item.exercise?.speech?.let { speechEnd(dataFromWork, it)}
                if ( currentIndRound != item.round?.idRound )
                    item.round?.speech?.let { speechEnd(dataFromWork, it)}
                currentIndExercise = item.exercise?.idExercise ?: 0
                currentIndRound = item.round?.idRound ?: 0
            }
            speechEnd(dataFromWork, tr.speech)
            dataFromWork.runningState.value = RunningState.Stopped
        }
    }

    suspend fun executeSet(set: Set?, dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataForWork.sendStepTraining()
        dataFromWork.trap()
        while (!runningCountRest.value) { delay(100L) }
//        runningCountRest.wait()
        set?.let { currentSet->
            dataFromWork.countRest.value = 0
            dataFromWork.phaseWorkout.value = 1
            speakSetBegin(set = currentSet, dataFromWork = dataFromWork)
            speakSetBody(set = currentSet, dataForWork = dataForWork, dataFromWork = dataFromWork)
            speakEnd(currentSet, dataFromWork)
        }
    }
    private suspend fun speakSetBegin(set: Set, dataFromWork: DataFromWork){
        speechManager.speech(dataFromWork, SpeechDB(message = textBeforeSet(set)))
        speechStart(dataFromWork, set.speech)
    }
    private suspend fun speakSetBody(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork){
        when (set.goal){
            GoalSet.COUNT -> speakingCOUNT(set, dataForWork, dataFromWork)
            GoalSet.COUNT_GROUP -> speakingCOUNTGROUP(set, dataFromWork)
            GoalSet.DURATION -> speakingDURATION(set, dataFromWork)
            GoalSet.DISTANCE -> speakingDISTANCE(set, dataFromWork)
        }
    }
    private suspend fun speakingCOUNT(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork ){
        dataFromWork.enableChangeInterval.value = true
        for (count in 1..set.reps){
            dataFromWork.currentCount.value = count
            speechManager.speakOutFlush(text = count.toString(), dataFromWork)
            val interval =
                if ( dataForWork.idSetChangeInterval.value == set.idSet &&
                    dataForWork.interval.value != set.intervalReps) {
                    dataForWork.sendStepTraining()
                    dataForWork.interval.value
                } else set.intervalReps
            lg("interval $interval")
            delayMy(((interval * 1000).toLong()), dataFromWork.runningState)
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
            runningCountRest.notify()
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
    suspend fun speechStart(dataFromWork: DataFromWork, speech: SpeechKit){
        speechManager.speech(dataFromWork, speech.beforeStart)
        speechManager.speech(dataFromWork, speech.afterStart)
    }
    suspend fun speechEnd(dataFromWork: DataFromWork, speech: SpeechKit){
        speechManager.speech(dataFromWork, speech.beforeEnd)
        speechManager.speech(dataFromWork, speech.afterEnd)
    }
}


//    val lock = Object()
//    fun main() {
//        runBlocking(Dispatchers.Default) {
//            launch(Dispatchers.IO) {
//                testWaitThread1()
//            }
//            launch(Dispatchers.IO) {
//                testWaitThread2()
//            }
//        }
//    }
//    fun testWaitThread1() = synchronized(lock) {
//        lock.wait()
//        println("Print first")
//    }
//    fun testWaitThread2() = synchronized(lock) {
//        println("Print second")
//        lock.notify()
//    }
