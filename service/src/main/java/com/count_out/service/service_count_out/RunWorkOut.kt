package com.count_out.service.service_count_out

import android.content.Context
import com.count_out.data.models.SetImpl
import com.count_out.data.models.SpeechImpl
import com.count_out.data.router.models.DataForWork
import com.count_out.data.router.models.DataFromWork
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.enums.Units
import com.count_out.framework.text_to_speech.SpeechManager
import com.count_out.service.service_timing.Delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.count_out.service.R

class RunWorkOut @Inject constructor(
    private val speechManager:SpeechManager, private val context: Context) {

    private val runningCountRest: MutableStateFlow<Boolean> = MutableStateFlow(true)
//    val runningCountRest = Object()

    suspend fun runWorkOut(dataForWork: DataForWork, dataFromWork: DataFromWork){

        var currentIndRound = 0L
        var currentIndExercise = 0L

        dataForWork.training.value?.let { tr->
            com.count_out.domain.entity.workout.Speech.addMessage =
                com.count_out.domain.entity.workout.Training.name
            speechStart(dataFromWork, com.count_out.domain.entity.workout.Training.speech as com.count_out.domain.entity.workout.SpeechKit)
            dataForWork.map.forEachIndexed { indM, item->
                dataForWork.indexMap = indM
                if ( currentIndRound != com.count_out.domain.entity.workout.Round.idRound) {
                    dataForWork.sendStepTrainingShort()
                    com.count_out.domain.entity.workout.Round.speech?.let { speechStart(dataFromWork, it) }
                }
                if ( currentIndExercise != com.count_out.domain.entity.workout.Exercise.idExercise) {
                    dataForWork.sendStepTrainingShort()
                    com.count_out.domain.entity.StepTraining.exercise?.let { exerciseLet->
                        speechStart(dataFromWork, com.count_out.domain.entity.workout.Exercise.speech as com.count_out.domain.entity.workout.SpeechKit)
                        val desc = if (dataForWork.enableSpeechDescription.value) com.count_out.domain.entity.workout.Activity.description else ""
                        speechManager.speech(dataFromWork, SpeechImpl(
                            message = "${context.getString(R.string.next_exercise)} ${com.count_out.domain.entity.workout.Activity.name}. $desc",
                            idSpeech = TODO(),
                            duration = TODO(),
                            addMessage = TODO()
                        ))
                    }
//                    dataForWork.setExecuteInfoExercise(index = indM)
                }
                executeSet( com.count_out.domain.entity.StepTraining.currentSet as SetImpl, dataForWork, dataFromWork )
                if ( currentIndExercise != com.count_out.domain.entity.workout.Exercise.idExercise)
                    com.count_out.domain.entity.workout.Exercise.speech?.let { speechEnd(dataFromWork, it)}
                if ( currentIndRound != com.count_out.domain.entity.workout.Round.idRound)
                    com.count_out.domain.entity.workout.Round.speech?.let { speechEnd(dataFromWork, it)}
                currentIndExercise = com.count_out.domain.entity.workout.Exercise.idExercise ?: 0
                currentIndRound = com.count_out.domain.entity.workout.Round.idRound ?: 0
            }
            speechEnd(dataFromWork, com.count_out.domain.entity.workout.Training.speech as com.count_out.domain.entity.workout.SpeechKit)
            dataFromWork.runningState.value = com.count_out.domain.entity.enums.RunningState.Stopped
        }
    }

    private suspend fun executeSet(set: SetImpl?, dataForWork: DataForWork, dataFromWork: DataFromWork){
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
    private suspend fun speakSetBegin(set: SetImpl, dataFromWork: DataFromWork){
        speechManager.speech(dataFromWork, SpeechImpl(
            message = textBeforeSet(set),
            idSpeech = TODO(),
            duration = TODO(),
            addMessage = TODO()
        ))
        speechStart(dataFromWork, set.speech as com.count_out.domain.entity.workout.SpeechKit)
    }
    private suspend fun speakSetBody(set: SetImpl, dataForWork: DataForWork, dataFromWork: DataFromWork){
        when (set.goal){
            com.count_out.domain.entity.enums.Goal.Distance -> speakingDISTANCE(set, dataFromWork)
            com.count_out.domain.entity.enums.Goal.Duration -> speakingDURATION(set, dataFromWork)
            com.count_out.domain.entity.enums.Goal.Count -> speakingCOUNT(set, dataForWork, dataFromWork)
            com.count_out.domain.entity.enums.Goal.CountGroup -> speakingCOUNTGROUP(set, dataFromWork)
        }
    }
    private suspend fun speakingCOUNT(set: SetImpl, dataForWork: DataForWork, dataFromWork: DataFromWork ){
        dataFromWork.enableChangeInterval.value = true
        for (count in 1..set.reps){
            dataFromWork.currentCount.value = count
            speechManager.speakOutFlush(text = count.toString(), dataFromWork)
            val interval =
                if ( dataForWork.idSetChangeInterval.value == set.idSet ) {
                    dataForWork.sendStepTraining()
                    dataForWork.interval.value
                } else set.intervalReps
            Delay().run(((interval * 1000).toLong()), dataFromWork.runningState)
        }
        dataFromWork.enableChangeInterval.value = false
    }
    private suspend fun speakingCOUNTGROUP(set: SetImpl, dataFromWork: DataFromWork){
        val listWordCount = set.groupCount.split(",")
        if ( listWordCount.isNotEmpty()){
            for (count in 0..< set.reps){
                speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataFromWork)
                Delay().run((set.intervalReps * 1000).toLong(), dataFromWork.runningState)
            }
        }
    }
    private suspend fun speakingDURATION(set: SetImpl, dataFromWork: DataFromWork){
        speakInterval( duration = (com.count_out.domain.entity.workout.Parameter.value / (if (com.count_out.domain.entity.workout.Parameter.unit == com.count_out.domain.entity.enums.Units.M) 60.0 else 1.0)).toInt(),
            dataFromWork = dataFromWork)
    }
    private fun speakingDISTANCE(set: SetImpl, dataFromWork: DataFromWork ){}

    private suspend fun speakEnd(setCurrent: SetImpl, dataFromWork: DataFromWork){
        speechEnd(dataFromWork, setCurrent.speech as com.count_out.domain.entity.workout.SpeechKit)
        CoroutineScope(Dispatchers.IO).launch { speakingRest(setCurrent, dataFromWork)}
        speechManager.speech(dataFromWork, SpeechImpl(
            message = getStr(R.string.rest) + "  " +
                    getPlurals(
                        com.count_out.domain.entity.workout.Parameter.value / (if (com.count_out.domain.entity.workout.Parameter.unit == com.count_out.domain.entity.enums.Units.M) 60.0 else 1.0),
                        com.count_out.domain.entity.workout.Parameter.unit.id),
            idSpeech = TODO(),
            duration = TODO(),
            addMessage = TODO()
        ))
    }
    private suspend fun speakingRest(set: SetImpl, dataFromWork: DataFromWork ){
        dataFromWork.phaseWorkout.value = 0
        if (com.count_out.domain.entity.workout.Parameter.value > 0) {
            runningCountRest.value = false
            speakInterval(duration = com.count_out.domain.entity.workout.Parameter.value.toInt(), dataFromWork = dataFromWork)
            runningCountRest.value = true
//            runningCountRest.notify()
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
            if (dataFromWork.runningState.value == com.count_out.domain.entity.enums.RunningState.Stopped) return
            if ( count % divider == 0 && !speechManager.getSpeeching())
                speechManager.speakOutFlushBusy(text = count.toString(), dataFromWork)
            dataFromWork.currentDuration.value = if (runningCountRest.value) count else 0
            dataFromWork.countRest.value = if (!runningCountRest.value) count else 0
            Delay().run(1000L, dataFromWork.runningState)
        }
    }
    private fun textBeforeSet(set: SetImpl): String{
        return when (set.goal){
            com.count_out.domain.entity.enums.Goal.Count -> " " + getPlurals(set.reps.toDouble(), R.plurals.repeat)
            com.count_out.domain.entity.enums.Goal.CountGroup -> " " + getPlurals(set.reps.toDouble(), R.plurals.repeat)
            com.count_out.domain.entity.enums.Goal.Duration ->
                getPlurals((com.count_out.domain.entity.workout.Parameter.value / (if (com.count_out.domain.entity.workout.Parameter.unit == com.count_out.domain.entity.enums.Units.M) 60.0 else 1.0)), com.count_out.domain.entity.workout.Parameter.unit.id)
            com.count_out.domain.entity.enums.Goal.Distance ->
                getPlurals(com.count_out.domain.entity.workout.Parameter.value / (if (com.count_out.domain.entity.workout.Parameter.unit == com.count_out.domain.entity.enums.Units.KM) 1000 else 1), com.count_out.domain.entity.workout.Parameter.unit.id)
        }
    }
    private fun getPlurals(count: Double, id: Int): String {

        return context.resources.getQuantityString(id, count.toInt(), count)
    }
    private fun getStr(id: Int) = context.getString(id)
    fun trap(){}
    private suspend fun speechStart(dataFromWork: DataFromWork, speech: com.count_out.domain.entity.workout.SpeechKit){
        com.count_out.domain.entity.workout.SpeechKit.beforeStart?.let { speechManager.speech(dataFromWork, it)}
        com.count_out.domain.entity.workout.SpeechKit.afterStart?.let { speechManager.speech(dataFromWork, it)}
    }
    private suspend fun speechEnd(dataFromWork: DataFromWork, speech: com.count_out.domain.entity.workout.SpeechKit){
        com.count_out.domain.entity.workout.SpeechKit.beforeEnd?.let { speechManager.speech(dataFromWork, it)}
        com.count_out.domain.entity.workout.SpeechKit.afterEnd?.let { speechManager.speech(dataFromWork, it)}
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
