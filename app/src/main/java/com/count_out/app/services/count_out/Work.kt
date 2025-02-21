package com.count_out.app.services.count_out

import com.count_out.app.device.text_to_speech.SpeechManager
import com.count_out.app.entity.RunningState
import com.count_out.app.domain.router.models.DataForWork
import com.count_out.app.domain.router.models.DataFromWork
import com.count_out.app.device.timer.Ticker
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

