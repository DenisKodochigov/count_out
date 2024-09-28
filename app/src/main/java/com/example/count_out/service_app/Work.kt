package com.example.count_out.service_app

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.service.player.SpeakingWork
import com.example.count_out.service.stopwatch.Watcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Work @Inject constructor(){
    @Inject lateinit var speakingWork: SpeakingWork
    @Inject lateinit var speechManager: SpeechManager

    fun start(sendToUI: SendToUI?, sendToWork: SendToService?, stopWorkout: ()->Unit){
        speechManager.init {
            getTick(sendToUI)
            speaking(sendToUI, sendToWork,  stopWorkout)
        }
    }
    fun stop(){
        Watcher.stop()
        speechManager.stopTts()
    }

    private fun speaking(sendToUI: SendToUI?, sendToWork: SendToService?, stopWorkout: ()->Unit) {
        sendToUI?.let { toUI->
            sendToWork?.let { toWork->
                CoroutineScope(Dispatchers.Default).launch {
                    toUI.mark.value = toWork.training.value?.let {
                        toUI.mark.value.copy(idTraining = it.idTraining.toInt()) }!!
                    speakingWork.playingWorkOut( toWork.toSendToWorkService(), toUI)
                    stopWorkout()
                }
            }
        }
    }
    private fun getTick(sendToUI: SendToUI?){
        sendToUI?.let {
            Watcher.start(it.runningState)
            CoroutineScope(Dispatchers.Default).launch {
                Watcher.getTickTime().collect{ tick ->
                    if (sendToUI.runningState.value == RunningState.Stopped) return@collect
                    it.flowTick.value = tick
                }
            }
        }
    }
}
