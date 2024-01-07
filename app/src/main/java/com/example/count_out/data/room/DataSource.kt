package com.example.count_out.data.room

import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.Set
import com.example.count_out.entity.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSource @Inject constructor(private val dataDao: DataDao) {
    fun getTraining(id: Long): TrainingDB = dataDao.getTrainingRel(id).toTraining()
    fun getTrainings(): List<Training> = dataDao.getTrainingsRel().map { it.toTraining() }
    fun updateTraining(trainingDB: TrainingDB): Int = dataDao.updateTraining(trainingDB)
    fun addTraining(): List<Training> {
        createTraining()
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    fun deleteTraining(id: Long): List<Training>{
        dataDao.delTraining(id)
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    fun copyTraining(id: Long): List<Training>{
        val currentTraining = dataDao.getTrainingRel(id).toTraining()
        val newTraining = currentTraining.copy(
            idTraining = 0L,
            speechId = dataDao.addSpeech(dataDao.getSpeech(currentTraining.speechId)))

        //Сделать копии Rounds, Exercise, Set, Speech

        dataDao.addTraining(newTraining)
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    fun changeNameTraining(training: Training, name: String): Training {
        (training as TrainingDB).name = name
        dataDao.updateTraining(training)
        return training
    }
    fun getNameTrainingFromRound(roundId: Long): String {
        val trainingId = dataDao.getIdTrainingFormRound(roundId)
        val trainingName = dataDao.getNameTraining(trainingId)
        return "$trainingName:$trainingId"
    }
    private fun createTraining(): Long {
        val trainingId = dataDao.addTraining(
            TrainingDB(name = "Training", speechId = dataDao.addSpeech(SpeechDB())))
        createRound(trainingId, RoundType.UP)
        createRound(trainingId, RoundType.OUT)
        createRound(trainingId, RoundType.DOWN)
        return trainingId
    }
//Round
    fun updateRound(round: RoundDB): Int = dataDao.updateRound(round)
    fun getNameRound(roundId: Long): String = dataDao.getNameRound(roundId)
//Exercise
    fun getExercise(roundId: Long, exerciseId:Long): Exercise {
        return  if (exerciseId < 1) { createExercise(roundId) }
                else { dataDao.getExerciseRel(exerciseId).toExercise() }
    }
    fun getExercises(roundId: Long): List<Exercise> = dataDao.getExercisesForRoundRel(roundId).map { it.toExercise() }
    fun updateExercise(exerciseDB: ExerciseDB): Int = dataDao.updateExercise(exerciseDB)

//Speech
    fun addSpeech(speech: SpeechDB): Long = dataDao.addSpeech(speech)
    fun updateSpeech(speech: SpeechDB): Int = dataDao.updateSpeech(speech)

//Activity
    fun getActivities(): List<ActivityDB> = dataDao.getActivities()
    fun setActivityToExercise(exerciseId: Long, activityId: Long): Exercise {
        return dataDao.getExerciseRel(
            dataDao.changeActivityExercise(exerciseId, activityId).toLong()).toExercise()
    }
    fun setColorActivity(activityId: Long, color: Int): ActivityDB{
        return dataDao.getActivity( dataDao.setColorActivity(activityId, color).toLong() )
    }
//Sets
    fun addUpdateSet(exerciseId:Long, set: Set): Exercise{
        if (set.idSet < 1) { dataDao.addSet(set as SetDB) }
        else { dataDao.updateSet( set as SetDB) }
        return dataDao.getExerciseRel(exerciseId).toExercise()
    }
    //######################################################################################

    private fun createRound( trainingId: Long, typeRound: RoundType){
        dataDao.addRound(
            RoundDB(
                trainingId = trainingId,
                roundType = typeRound,
                speechId = dataDao.addSpeech(SpeechDB())
            )
        )
    }
    private fun createExercise( roundId: Long): ExerciseDB {
        return dataDao.getExercise(
            dataDao.addExercise(
                ExerciseDB(roundId = roundId, speechId = dataDao.addSpeech(SpeechDB())))
        )
    }
}