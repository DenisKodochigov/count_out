package com.example.count_out.service_count_out.work.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteExerciseN @Inject constructor(
    val speechManager:SpeechManager, private val executeSet: ExecuteSetN
){

    suspend fun executeExercise(dataForWork: DataForWork, dataFromWork: DataFromWork){
        if (dataFromWork.runningState.value == RunningState.Stopped) dataFromWork.cancelCoroutineWork()
        dataForWork.getExercise()?.speech?.beforeStart?.addMessage = dataForWork.getExercise()?.activity?.name + "."
        if (dataForWork.enableSpeechDescription.value)
            dataForWork.getExercise()?.speech?.beforeStart?.addMessage += dataForWork.getExercise()?.activity?.description
        dataForWork.getExercise()?.speech?.beforeStart?.let { speechManager.speech(dataFromWork, it)}
        dataForWork.getExercise()?.speech?.afterStart?.let { speechManager.speech(dataFromWork,it) }
        dataForWork.getExercise()?.sets?.forEachIndexed { index, _->
            dataFromWork.mark.value = dataFromWork.mark.value.copy(idSet = index)
            executeSet.executeSet( dataForWork.apply { indexSet = index }, dataFromWork)
        }
        dataForWork.getExercise()?.speech?.beforeEnd?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getExercise()?.speech?.afterEnd?.let { speechManager.speech(dataFromWork, it) }
    }
}
