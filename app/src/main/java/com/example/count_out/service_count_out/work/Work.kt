package com.example.count_out.service_count_out.work

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import com.example.count_out.service_count_out.stopwatch.Watcher
import com.example.count_out.service_count_out.work.execute.ExecuteWork
import com.example.count_out.service_count_out.work.execute.RunWorkOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

class Work @Inject constructor(
    val speechManager: SpeechManager,
    val executeWork: ExecuteWork,
    val runWorkOut: RunWorkOut){

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
            dataFromWork.trap = {
                if (dataFromWork.runningState.value == RunningState.Stopped) {
                    dataFromWork.empty()
                    dataForWork.empty()
                    stop()
                } }
//            executeWork.executeWorkOut( dataForWork, dataFromWork)
            runWorkOut.runWorkOut(dataForWork, dataFromWork)
        }
    }
    private fun getTick( dataFromWork: DataFromWork ){
        Watcher.start(dataFromWork.runningState)
        CoroutineScope(Dispatchers.Default).launch {
            Watcher.getTickTime().collect{ tick -> dataFromWork.flowTime.value = tick }
        }
    }
}

