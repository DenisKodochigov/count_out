package com.example.count_out.service_count_out.workout.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.RunningState
import javax.inject.Inject

class ExecuteExercise @Inject constructor(
    val speechManager:SpeechManager,
    private val executeSet: ExecuteSet,)
{
    suspend fun executeExercise(dataForWork: DataForServ, dataForUI: DataForUI, ){
        if (dataForUI.runningState.value == RunningState.Stopped) dataForUI.cancelCoroutineWork()
        dataForWork.getExercise()?.speech?.beforeStart?.addMessage = dataForWork.getExercise()?.activity?.name + "."
        if (dataForWork.enableSpeechDescription.value)
            dataForWork.getExercise()?.speech?.beforeStart?.addMessage += dataForWork.getExercise()?.activity?.description
        dataForWork.getExercise()?.speech?.beforeStart?.let { speechManager.speech(dataForUI, it)}
        dataForWork.getExercise()?.speech?.afterStart?.let { speechManager.speech(dataForUI,it) }
        dataForWork.getExercise()?.sets?.forEachIndexed { index, _->
            dataForUI.mark.value = dataForUI.mark.value.copy(idSet = index)
            executeSet.executeSet( dataForWork.apply { indexSet = index }, dataForUI)
        }
        dataForWork.getExercise()?.speech?.beforeEnd?.let { speechManager.speech(dataForUI, it) }
        dataForWork.getExercise()?.speech?.afterEnd?.let { speechManager.speech(dataForUI, it) }
    }
}
