package com.example.count_out.service_count_out.work

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Mark
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import com.example.count_out.service_count_out.stopwatch.Watcher
import com.example.count_out.service_count_out.work.execute.ExecuteWorkN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class WorkN @Inject constructor(val speechManager: SpeechManager, val executeWork: ExecuteWorkN){

    fun start(dataFromWork: DataFromWork, dataForWork: DataForWork){
        speechManager.init {
            getTick( dataFromWork )
            speaking( dataFromWork, dataForWork )
        }
    }
    fun stop(){
        Watcher.stop()
        speechManager.stopTts()
        throw CancellationException()
    }
    private fun speaking(dataFromWork: DataFromWork, dataForWork: DataForWork) {
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.mark.value = Mark(idTraining = dataForWork.training.value?.idTraining?.toInt() ?: 0)
            dataFromWork.cancelCoroutineWork = { stop() }
            executeWork.executeWorkOut( dataForWork, dataFromWork)
        }
    }
    private fun getTick( dataFromWork: DataFromWork ){
        Watcher.start(dataFromWork.runningState)
        CoroutineScope(Dispatchers.Default).launch {
            Watcher.getTickTime().collect{ tick ->
//                if (dataForUI.runningState.value == RunningState.Stopped) return@collect
                dataFromWork.flowTick.value = tick
            }
        }
    }
}
