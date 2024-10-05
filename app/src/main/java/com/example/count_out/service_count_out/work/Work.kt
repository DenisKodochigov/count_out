package com.example.count_out.service_count_out.work

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import com.example.count_out.service_count_out.stopwatch.Watcher
import com.example.count_out.service_count_out.work.execute.ExecuteWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class Work @Inject constructor(val speechManager: SpeechManager, val executeWork: ExecuteWork){

    fun start(dataForWork: DataForWork, dataFromWork: DataFromWork){
        speechManager.init {
            getTick( dataFromWork )
            speaking( dataForWork, dataFromWork )
        }
    }
    fun stop(){
        Watcher.stop()
        speechManager.stopTts()
        throw CancellationException()
    }
    private fun speaking(dataForWork: DataForWork, dataFromWork: DataFromWork) {
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.equalsStop = { if (dataFromWork.runningState.value == RunningState.Stopped) stop() }
            executeWork.executeWorkOut( dataForWork, dataFromWork)
        }
    }
    private fun getTick( dataFromWork: DataFromWork ){
        Watcher.start(dataFromWork.runningState)
        CoroutineScope(Dispatchers.Default).launch {
            Watcher.getTickTime().collect{ tick -> dataFromWork.flowTick.value = tick }
        }
    }
}

