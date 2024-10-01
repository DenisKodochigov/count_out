package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import javax.inject.Inject

class ExecuteRound @Inject constructor(
    val speechManager:SpeechManager,
    private val executeExercise: ExecuteExercise
)
{
    suspend fun executeRound(sendToSrv: DataForServ, dataForUI: DataForUI, ){
//        lg("playingRound ${sendToUI.runningState.value}")
        if (dataForUI.runningState.value == RunningState.Started) {
            sendToSrv.getRound()?.speech?.beforeStart?.let { speechManager.speech(dataForUI, it) }
            sendToSrv.getRound()?.speech?.afterStart?.let { speechManager.speech(dataForUI, it) }
            sendToSrv.getRound()?.exercise?.forEachIndexed { index, _->
                dataForUI.mark.value = dataForUI.mark.value.copy(idExercise = index)
                executeExercise.executeExercise( sendToSrv.apply { indexExercise = index }, dataForUI) }
            sendToSrv.getRound()?.speech?.beforeEnd?.let { speechManager.speech(dataForUI, it) }
            sendToSrv.getRound()?.speech?.afterEnd?.let { speechManager.speech(dataForUI, it) }
        }
    }
}