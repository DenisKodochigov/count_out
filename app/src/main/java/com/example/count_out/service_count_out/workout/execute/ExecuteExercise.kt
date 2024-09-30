package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import javax.inject.Inject

class ExecuteExercise @Inject constructor(
    val speechManager:SpeechManager,
    private val executeSet: ExecuteSet,)
{
    suspend fun executeExercise(sendToSrv: SendToService, sendToUI: SendToUI, ){
//        lg("playingExercise ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToSrv.getExercise()?.speech?.beforeStart?.addMessage = sendToSrv.getExercise()?.activity?.name + "."
            if (sendToSrv.enableSpeechDescription.value)
                sendToSrv.getExercise()?.speech?.beforeStart?.addMessage += sendToSrv.getExercise()?.activity?.description
            sendToSrv.getExercise()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it)}
            sendToSrv.getExercise()?.speech?.afterStart?.let { speechManager.speech(sendToUI,it) }
            sendToSrv.getExercise()?.sets?.forEachIndexed { index, _->
                sendToUI.mark.value = sendToUI.mark.value.copy(idSet = index)
                executeSet.executeSet( sendToSrv.apply { indexSet = index }, sendToUI)
            }
//            speechManager.speech(variablesOut, sendToSrv.getExercise().speech.beforeEnd)
//            speechManager.speech(variablesOut, sendToSrv.getExercise().speech.afterEnd)
        }
    }
}
