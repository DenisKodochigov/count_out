package com.example.count_out.service_count_out.work.execute

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteRoundN @Inject constructor(
    val speechManager:SpeechManager,
    private val executeExercise: ExecuteExerciseN
) {
    suspend fun executeRound(dataForWork: DataForWork, dataFromWork: DataFromWork){
        if (dataFromWork.runningState.value == RunningState.Stopped) dataFromWork.cancelCoroutineWork()
        dataForWork.getRound()?.speech?.beforeStart?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getRound()?.speech?.afterStart?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getRound()?.exercise?.forEachIndexed { index, _->
            dataFromWork.mark.value = dataFromWork.mark.value.copy(idExercise = index)
            executeExercise.executeExercise( dataForWork.apply { indexExercise = index }, dataFromWork) }
        dataForWork.getRound()?.speech?.beforeEnd?.let { speechManager.speech(dataFromWork, it) }
        dataForWork.getRound()?.speech?.afterEnd?.let { speechManager.speech(dataFromWork, it) }
    }
}