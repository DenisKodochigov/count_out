package com.example.count_out.data.room

import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSource @Inject constructor(private val dataDao: DataDao) {
    fun getTraining(id: Long): TrainingDB {
        val trainingId = if (id < 0) createTrainig() else id
        return dataDao.getTraining(trainingId)
    }
    fun getTrainings(): List<Training> = dataDao.getTrainings()
    fun addTraining(trainingDB: TrainingDB): Int = dataDao.updateTraining(trainingDB)
    fun updateTraining(trainingDB: TrainingDB): Int = dataDao.updateTraining(trainingDB)
    fun deleteTraining(id: Long): List<Training>{
        dataDao.delTraining(id)
        return dataDao.getTrainings()
    }
    fun copyTraining(id: Long): List<Training>{
        dataDao.addTraining(dataDao.getTraining(id))
        return dataDao.getTrainings()
    }
    fun changeNameTraining(training: Training, name: String): Training {
        (training as TrainingDB).name = name
        dataDao.updateTraining(training)
        return training
    }

    fun createTrainig(): Long{
        val trainingId = dataDao.addTraining(
            TrainingDB( name = "Training", isSelected = false, speechId = dataDao.addSpeech(SpeechDB()) ))

        val roundId = dataDao.addRound(
            RoundDB( trainingId = trainingId, speechId = dataDao.addSpeech(SpeechDB())))
        if (dataDao.getActivity(0) == null) dataDao.addActivity(ActivityDB())

        val exerciseId = dataDao.addExercise(
            ExerciseDB(
                roundId = roundId, activityId = 0L, speechId = dataDao.addSpeech(SpeechDB())))
        val setId = dataDao.addSet(
            SetDB(exerciseId = exerciseId, name = "Set", speechId = dataDao.addSpeech(SpeechDB())))
        return trainingId
    }


//    fun getExercise(id: Long): ExerciseDB {
//        val exerciseId = if (id < 0) createExercise() else id
//        return dataDao.getExercise(exerciseId)
//    }
//    private fun createExercise(): Long{
//        return dataDao.addExercise(
//            ExerciseDB(
//                roundId = roundId, activityId = 0L, speechId = dataDao.addSpeech(SpeechDB())))
//    }
//    fun getExercise(): List<Training> = dataDao.getExercise()
    fun updateExercise(exerciseDB: ExerciseDB): Int = dataDao.updateExercise(exerciseDB)
    fun updateRound(round: RoundDB): Int = dataDao.updateRound(round)
    fun addSpeech(speech: SpeechDB): Long = dataDao.addSpeech(speech)
    fun updateSpeech(speech: SpeechDB): Int = dataDao.updateSpeech(speech)
}