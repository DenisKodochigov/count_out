package com.count_out.service.service_count_out

import com.count_out.service.service_timing.Ticker
import com.count_out.data.router.models.DataForWork
import com.count_out.data.router.models.DataFromWork
import com.count_out.domain.entity.enums.RunningState
import com.count_out.framework.text_to_speech.SpeechManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

class Work @Inject constructor(
    val speechManager: SpeechManager,
    val runWorkOut: RunWorkOut
){

    fun start(dataForWork: DataForWork, dataFromWork: DataFromWork){
        speechManager.init {
            getTick( dataFromWork )
            speaking( dataForWork, dataFromWork )
        }
    }
    fun stop(){
        Ticker.stop()
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
            runWorkOut.runWorkOut(dataForWork, dataFromWork)
        }
    }
    private fun getTick( dataFromWork: DataFromWork ){
        Ticker.start(dataFromWork.runningState)
        CoroutineScope(Dispatchers.Default).launch {
            Ticker.getTickTime().collect{ tick -> dataFromWork.flowTime.value = tick }
        }
    }
}

