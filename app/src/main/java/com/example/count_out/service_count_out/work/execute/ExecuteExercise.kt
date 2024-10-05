package com.example.count_out.service_count_out.work.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteExercise @Inject constructor(
    val speechManager:SpeechManager, private val executeSet: ExecuteSet
){
    suspend fun executeExercise(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        dataForWork.getExercise()?.speech?.beforeStart?.let { speechManager.speech(dataFromWork, it)}
        dataForWork.getExercise()?.speech?.afterStart?.let { speechManager.speech(dataFromWork,it) }
        dataForWork.getExercise()?.sets?.forEachIndexed { index, _->
            executeSet.executeSet( dataForWork.apply { indexSet = index }, dataFromWork) }
        dataForWork.getExercise()?.speech?.beforeEnd?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getExercise()?.speech?.afterEnd?.let { speechManager.speech(dataFromWork, it) }
    }
}
