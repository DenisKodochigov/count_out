package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import javax.inject.Inject

class ExecuteExercise @Inject constructor(
    val speechManager:SpeechManager,
    private val executeSet: ExecuteSet,)
{
    suspend fun executeExercise(sendToSrv: DataForServ, dataForUI: DataForUI, ){
//        lg("playingExercise ${sendToUI.runningState.value}")
        if (dataForUI.runningState.value == RunningState.Started) {
            sendToSrv.getExercise()?.speech?.beforeStart?.addMessage = sendToSrv.getExercise()?.activity?.name + "."
            if (sendToSrv.enableSpeechDescription.value)
                sendToSrv.getExercise()?.speech?.beforeStart?.addMessage += sendToSrv.getExercise()?.activity?.description
            sendToSrv.getExercise()?.speech?.beforeStart?.let { speechManager.speech(dataForUI, it)}
            sendToSrv.getExercise()?.speech?.afterStart?.let { speechManager.speech(dataForUI,it) }
            sendToSrv.getExercise()?.sets?.forEachIndexed { index, _->
                dataForUI.mark.value = dataForUI.mark.value.copy(idSet = index)
                executeSet.executeSet( sendToSrv.apply { indexSet = index }, dataForUI)
            }
//            speechManager.speech(variablesOut, sendToSrv.getExercise().speech.beforeEnd)
//            speechManager.speech(variablesOut, sendToSrv.getExercise().speech.afterEnd)
        }
    }
}
