package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import javax.inject.Inject

class ExecuteRound @Inject constructor(
    val speechManager:SpeechManager,
    private val executeExercise: ExecuteExercise
)
{
    suspend fun executeRound(sendToSrv: SendToService, sendToUI: SendToUI, ){
//        lg("playingRound ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            sendToSrv.getRound()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            sendToSrv.getRound()?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }
            sendToSrv.getRound()?.exercise?.forEachIndexed { index, _->
                sendToUI.mark.value = sendToUI.mark.value.copy(idExercise = index)
                executeExercise.executeExercise( sendToSrv.apply { indexExercise = index }, sendToUI) }
            sendToSrv.getRound()?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it) }
            sendToSrv.getRound()?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
        }
    }
}