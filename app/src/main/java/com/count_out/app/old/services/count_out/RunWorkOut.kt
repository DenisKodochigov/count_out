//package com.count_out.app.services.count_out
//
//import android.content.Context
//import com.count_out.app.R
//import com.count_out.app.device.text_to_speech.SpeechManager
//import com.count_out.app.device.timer.Delay
//import com.count_out.app.domain.router.models.DataForWork
//import com.count_out.app.domain.router.models.DataFromWork
//import com.count_out.app.ui.view_components.lg
//import com.count_out.data.entity.SpeechImpl
//import com.count_out.entity.entity.workout.Set
//import com.count_out.entity.entity.workout.SpeechKit
//import com.count_out.domain.entity.enums.Goal
//import com.count_out.domain.entity.enums.RunningState
//import com.count_out.domain.entity.enums.Units
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.launch
//import okhttp3.internal.notify
//import javax.inject.Inject
//
//class RunWorkOut @Inject constructor( val speechManager:SpeechManager, val context: Context) {
//
//    private val runningCountRest: MutableStateFlow<Boolean> = MutableStateFlow(true)
////    val runningCountRest = Object()
//
//    suspend fun runWorkOut(dataForWork: DataForWork, dataFromWork: DataFromWork){
//
//        var currentIndRound = 0L
//        var currentIndExercise = 0L
//
//        dataForWork.training.value?.let { tr->
//            tr.speech?.beforeStart?.addMessage = tr.name
//            speechStart(dataFromWork, tr.speech as SpeechKit)
//            dataForWork.map.forEachIndexed { indM, item->
//                dataForWork.indexMap = indM
//                if ( currentIndRound != item.round?.idRound ) {
//                    dataForWork.sendStepTrainingShort()
//                    item.round?.speech?.let { speechStart(dataFromWork, it) }
//                }
//                if ( currentIndExercise != item.exercise?.idExercise ) {
//                    dataForWork.sendStepTrainingShort()
//                    item.exercise?.let { exerciseLet->
//                        speechStart(dataFromWork, exerciseLet.speech as SpeechKit)
//                        val desc = if (dataForWork.enableSpeechDescription.value) exerciseLet.activity?.description else ""
//                        speechManager.speech(dataFromWork, SpeechImpl(
//                            message =
//                            "${context.getString(R.string.next_exercise)} ${exerciseLet.activity?.name}. $desc",
//                            idSpeech = TODO(),
//                            duration = TODO(),
//                            addMessage = TODO()
//                        ))
//                    }
////                    dataForWork.setExecuteInfoExercise(index = indM)
//                }
//                executeSet( item.currentSet, dataForWork, dataFromWork )
//                if ( currentIndExercise != item.exercise?.idExercise )
//                    item.exercise?.speech?.let { speechEnd(dataFromWork, it)}
//                if ( currentIndRound != item.round?.idRound )
//                    item.round?.speech?.let { speechEnd(dataFromWork, it)}
//                currentIndExercise = item.exercise?.idExercise ?: 0
//                currentIndRound = item.round?.idRound ?: 0
//            }
//            speechEnd(dataFromWork, tr.speech as SpeechKit)
//            dataFromWork.runningState.value = RunningState.Stopped
//        }
//    }
//
//    private suspend fun executeSet(set: Set?, dataForWork: DataForWork, dataFromWork: DataFromWork){
//        dataForWork.sendStepTraining()
//        dataFromWork.trap()
//        while (!runningCountRest.value) { delay(100L) }
////        runningCountRest.wait()
//        set?.let { currentSet->
//            dataFromWork.countRest.value = 0
//            dataFromWork.phaseWorkout.value = 1
//            speakSetBegin(set = currentSet, dataFromWork = dataFromWork)
//            speakSetBody(set = currentSet, dataForWork = dataForWork, dataFromWork = dataFromWork)
//            speakEnd(currentSet, dataFromWork)
//        }
//    }
//    private suspend fun speakSetBegin(set: Set, dataFromWork: DataFromWork){
//        speechManager.speech(dataFromWork, SpeechImpl(
//            message = textBeforeSet(set),
//            idSpeech = TODO(),
//            duration = TODO(),
//            addMessage = TODO()
//        ))
//        speechStart(dataFromWork, set.speech as SpeechKit)
//    }
//    private suspend fun speakSetBody(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork){
//        when (set.goal){
//            Goal.Count -> speakingCOUNT(set, dataForWork, dataFromWork)
//            Goal.CountGroup -> speakingCOUNTGROUP(set, dataFromWork)
//            Goal.Duration -> speakingDURATION(set, dataFromWork)
//            Goal.Distance -> speakingDISTANCE(set, dataFromWork)
//        }
//    }
//    private suspend fun speakingCOUNT(set: Set, dataForWork: DataForWork, dataFromWork: DataFromWork ){
//        dataFromWork.enableChangeInterval.value = true
//        for (count in 1..set.reps){
//            dataFromWork.currentCount.value = count
//            speechManager.speakOutFlush(text = count.toString(), dataFromWork)
//            val interval =
//                if ( dataForWork.idSetChangeInterval.value == set.idSet ) {
//                    dataForWork.sendStepTraining()
//                    dataForWork.interval.value
//                } else set.intervalReps
//            lg("interval $interval")
//            Delay().run(((interval * 1000).toLong()), dataFromWork.runningState)
//        }
//        dataFromWork.enableChangeInterval.value = false
//    }
//    private suspend fun speakingCOUNTGROUP(set: Set, dataFromWork: DataFromWork){
//        val listWordCount = set.groupCount.split(",")
//        if ( listWordCount.isNotEmpty()){
//            for (count in 0..< set.reps){
//                speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], dataFromWork)
//                Delay().run((set.intervalReps * 1000).toLong(), dataFromWork.runningState)
//            }
//        }
//    }
//    private suspend fun speakingDURATION(set: Set, dataFromWork: DataFromWork){
//        speakInterval( duration = (set.duration.value / (if (set.duration.unit == Units.M) 60.0 else 1.0)).toInt(),
//            dataFromWork = dataFromWork)
//    }
//    private fun speakingDISTANCE(set: Set, dataFromWork: DataFromWork ){}
//
//    private suspend fun speakEnd(setCurrent: Set, dataFromWork: DataFromWork){
//        speechEnd(dataFromWork, setCurrent.speech as SpeechKit)
//        CoroutineScope(Dispatchers.IO).launch { speakingRest(setCurrent, dataFromWork)}
//        speechManager.speech(dataFromWork, SpeechImpl(
//            message = getStr(R.string.rest) + "  " +
//                    getPlurals(
//                        setCurrent.rest.value / (if (setCurrent.duration.unit == Units.M) 60.0 else 1.0),
//                        setCurrent.rest.unit.id),
//            idSpeech = TODO(),
//            duration = TODO(),
//            addMessage = TODO()
//        ))
//    }
//    private suspend fun speakingRest(set: Set, dataFromWork: DataFromWork ){
//        dataFromWork.phaseWorkout.value = 0
//        if (set.rest.value > 0) {
//            runningCountRest.value = false
//            speakInterval(duration = set.rest.value.toInt(), dataFromWork = dataFromWork)
//            runningCountRest.value = true
//            runningCountRest.notify()
//        }
//    }
//    private suspend fun speakInterval(duration: Int, dataFromWork: DataFromWork) {
//        when (duration) {
//            in 0..20 -> countDelay(duration = duration, divider = 5, dataFromWork = dataFromWork)
//            in 20 .. 119 -> countDelay(duration = duration, divider = 10, dataFromWork = dataFromWork)
//            else -> countDelay(duration = duration, divider = 60, dataFromWork = dataFromWork)
//        }
//    }
//    private suspend fun countDelay(duration: Int, divider: Int, dataFromWork: DataFromWork){
//        for ( count in 1..duration){
//            if (dataFromWork.runningState.value == RunningState.Stopped) return
//            if ( count % divider == 0 && !speechManager.getSpeeching())
//                speechManager.speakOutFlushBusy(text = count.toString(), dataFromWork)
//            dataFromWork.currentDuration.value = if (runningCountRest.value) count else 0
//            dataFromWork.countRest.value = if (!runningCountRest.value) count else 0
//            Delay().run(1000L, dataFromWork.runningState)
//        }
//    }
//    private fun textBeforeSet(set: Set): String{
//        return when (set.goal){
//            Goal.Count -> " " + getPlurals(set.reps.toDouble(), R.plurals.repeat)
//            Goal.CountGroup -> " " + getPlurals(set.reps.toDouble(), R.plurals.repeat)
//            Goal.Duration ->
//                getPlurals((set.duration.value / (if (set.duration.unit == Units.M) 60.0 else 1.0)), set.duration.unit.id)
//            Goal.Distance ->
//                getPlurals(set.duration.value / (if (set.distance.unit == Units.KM) 1000 else 1), set.distance.unit.id)
//        }
//    }
//    private fun getPlurals(count: Double, id: Int): String {
//        return context.resources.getQuantityString(id, count.toInt(), count)
//    }
//    private fun getStr(id: Int) = context.getString(id)
//    fun trap(){}
//    private suspend fun speechStart(dataFromWork: DataFromWork, speech: SpeechKit){
//        speechManager.speech(dataFromWork, speech.beforeStart)
//        speechManager.speech(dataFromWork, speech.afterStart)
//    }
//    private suspend fun speechEnd(dataFromWork: DataFromWork, speech: SpeechKit){
//        speechManager.speech(dataFromWork, speech.beforeEnd)
//        speechManager.speech(dataFromWork, speech.afterEnd)
//    }
//}
//
//
////    val lock = Object()
////    fun main() {
////        runBlocking(Dispatchers.Default) {
////            launch(Dispatchers.IO) {
////                testWaitThread1()
////            }
////            launch(Dispatchers.IO) {
////                testWaitThread2()
////            }
////        }
////    }
////    fun testWaitThread1() = synchronized(lock) {
////        lock.wait()
////        println("Print first")
////    }
////    fun testWaitThread2() = synchronized(lock) {
////        println("Print second")
////        lock.notify()
////    }
