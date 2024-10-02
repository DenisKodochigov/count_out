package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.RunningState
import javax.inject.Inject

class ExecuteRound @Inject constructor(
    val speechManager:SpeechManager,
    private val executeExercise: ExecuteExercise
)
{
    suspend fun executeRound(dataForWork: DataForServ, dataForUI: DataForUI, ){
        if (dataForUI.runningState.value == RunningState.Stopped) dataForUI.cancelCoroutineWork()
        dataForWork.getRound()?.speech?.beforeStart?.let { speechManager.speech(dataForUI, it) }
        dataForWork.getRound()?.speech?.afterStart?.let { speechManager.speech(dataForUI, it) }
        dataForWork.getRound()?.exercise?.forEachIndexed { index, _->
            dataForUI.mark.value = dataForUI.mark.value.copy(idExercise = index)
            executeExercise.executeExercise( dataForWork.apply { indexExercise = index }, dataForUI) }
        dataForWork.getRound()?.speech?.beforeEnd?.let { speechManager.speech(dataForUI, it) }
        dataForWork.getRound()?.speech?.afterEnd?.let { speechManager.speech(dataForUI, it) }
    }
}