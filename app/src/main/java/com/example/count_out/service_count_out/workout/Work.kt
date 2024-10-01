package com.example.count_out.service_count_out.workout

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.Mark
import com.example.count_out.entity.RunningState
import com.example.count_out.service_count_out.stopwatch.Watcher
import com.example.count_out.service_count_out.workout.execute.ExecuteWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Work @Inject constructor(val speechManager: SpeechManager, val executeWork: ExecuteWork){

    fun start(dataForUI: DataForUI?, sendToWork: DataForServ?, stopWorkout: ()->Unit){
        speechManager.init {
            getTick(dataForUI)
            speaking(dataForUI, sendToWork,  stopWorkout)
        }
    }
    fun stop(){
        Watcher.stop()
        speechManager.stopTts()
    }

    private fun speaking(dataForUI: DataForUI?, sendToWork: DataForServ?, stopWorkout: ()->Unit) {
        dataForUI?.let { toUI->
            sendToWork?.let { toWork->
                CoroutineScope(Dispatchers.Default).launch {
                    toUI.mark.value = toWork.training.value?.let {
                        toUI.mark.value.copy(idTraining = it.idTraining.toInt()) } ?:Mark()
                    executeWork.executeWorkOut( toWork, toUI)
                    stop()
                    stopWorkout()
                }
            }
        }
    }
    private fun getTick(dataForUI: DataForUI?){
        dataForUI?.let {
            Watcher.start(it.runningState)
            CoroutineScope(Dispatchers.Default).launch {
                Watcher.getTickTime().collect{ tick ->
                    if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                    it.flowTick.value = tick
                }
            }
        }
    }
}
