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
import kotlin.coroutines.cancellation.CancellationException

class Work @Inject constructor(val speechManager: SpeechManager, val executeWork: ExecuteWork){

    fun start( dataForUI: DataForUI, dataForWork: DataForServ?){
        speechManager.init {
            getTick( dataForUI )
            speaking( dataForUI, dataForWork )
        }
    }
    fun stop(){
        Watcher.stop()
        speechManager.stopTts()
        throw CancellationException()
    }
    private fun speaking(dataForUI: DataForUI, dataForWork: DataForServ?) {
        if (dataForWork != null ) {
            CoroutineScope(Dispatchers.Default).launch {
                dataForUI.mark.value = Mark(idTraining = dataForWork.training.value?.idTraining?.toInt() ?: 0)
                dataForUI.cancelCoroutineWork = { stop() }
                executeWork.executeWorkOut( dataForWork, dataForUI)
            }
        }
    }
    private fun getTick( dataForUI: DataForUI ){
        Watcher.start(dataForUI.runningState)
        CoroutineScope(Dispatchers.Default).launch {
            Watcher.getTickTime().collect{ tick ->
                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                dataForUI.flowTick.value = tick
            }
        }
    }
}
