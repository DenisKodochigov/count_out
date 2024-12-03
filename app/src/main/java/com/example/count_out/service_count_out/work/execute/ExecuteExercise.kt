package com.example.count_out.service_count_out.work.execute

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.router.DataForWork
import com.example.count_out.entity.router.DataFromWork
import javax.inject.Inject

class ExecuteExercise @Inject constructor(
    val speechManager:SpeechManager, private val executeSet: ExecuteSet, val context: Context){
    suspend fun executeExercise(dataForWork: DataForWork, dataFromWork: DataFromWork){
        dataFromWork.equalsStop()
        dataForWork.getExercise()?.let { exercise ->
            speechManager.speech(dataFromWork, exercise.speech.beforeStart)
            val desc = if (dataForWork.enableSpeechDescription.value) exercise.activity.description else ""
            speechManager.speech(dataFromWork, SpeechDB(message =
                "${context.getString(R.string.next_exercise)} ${exercise.activity.name}. $desc"))
            speechManager.speech(dataFromWork, exercise.speech.afterStart)
            exercise.sets.forEachIndexed { index, _->
                executeSet.executeSet( dataForWork.apply { indexSet = index }, dataFromWork) }
            speechManager.speech(dataFromWork, exercise.speech.beforeEnd)
            speechManager.speech(dataFromWork, exercise.speech.afterEnd)
        }
    }
}
