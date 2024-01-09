package com.example.count_out.data

import com.example.count_out.data.room.DataSource
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Plugins
import com.example.count_out.entity.Round
import com.example.count_out.entity.Set
import com.example.count_out.entity.Speech
import com.example.count_out.entity.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(private val dataSource: DataSource){

    fun getTrainings(): List<Training> = dataSource.getTrainings()
    fun getTraining(id: Long): Training = dataSource.getTraining(id)

    fun addTraining(): List<Training> = dataSource.addTraining()
    fun deleteTraining(id: Long): List<Training> = dataSource.deleteTraining(id)
    fun copyTraining(id: Long): List<Training> = dataSource.copyTraining(id)
    fun changeNameTraining(training: Training, name: String): Training = dataSource.changeNameTraining(training, name)
    fun deleteTrainingNothing(id: Long){
        Plugins.listTr.remove(Plugins.listTr.find { it.idTraining == id })
    }
    fun setSpeech(speech: Speech, item: Any?)
    {
        val speechId = if (speech.idSpeech == -1L) dataSource.addSpeech(speech as SpeechDB)
                        else dataSource.updateSpeech(speech as SpeechDB)
        when (item) {
            is Training -> {
                (item as TrainingDB).speechId = speechId.toLong()
                dataSource.updateTraining(item)
            }
            is Exercise -> {
                (item as ExerciseDB).speechId = speechId.toLong()
                dataSource.updateExercise(item)
            }
            is Round -> {
                (item as RoundDB).speechId = speechId.toLong()
                dataSource.updateRound(item)
            }
            else -> { }
        }
    }
    fun getExercise(roundId: Long, exerciseId:Long): Exercise {
        return if (roundId < 1) ExerciseDB()
                else dataSource.getExercise(roundId, exerciseId)
    }
    fun copyExercise (trainingId: Long, exerciseId: Long): Training {
        return dataSource.copyExercise( trainingId, exerciseId)
    }
    fun deleteExercise(trainingId: Long, exerciseId: Long): Training {
        return dataSource.deleteExercise( trainingId, exerciseId)
    }
    fun getNameTrainingFromRound(roundId: Long): String = dataSource.getNameTrainingFromRound(roundId)
    fun getNameRound(roundId: Long): String = dataSource.getNameRound(roundId)

//###### ACTIVITY ##################
    fun getActivities(): List<Activity> = dataSource.getActivities()
    fun setActivityToExercise(exerciseId: Long, activityId: Long): Exercise =
        dataSource.setActivityToExercise(exerciseId = exerciseId, activityId = activityId)

    fun onSetColorActivity(activityId: Long, color: Int) = dataSource.setColorActivity(activityId, color)

    //###### SET ##################
    fun addUpdateSet(exerciseId:Long, set: Set): Exercise = dataSource.addUpdateSet(exerciseId, set)

}