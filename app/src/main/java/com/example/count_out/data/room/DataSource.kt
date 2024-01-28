package com.example.count_out.data.room

import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Round
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.Set
import com.example.count_out.entity.Speech
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
    fun getRound(roundId: Long): RoundDB = dataDao.getRoundRel(roundId).toRound()
//Exercise
    fun getExercise( exerciseId:Long): Exercise {
        return if (exerciseId > 1) { dataDao.getExerciseRel(exerciseId).toExercise() }
                else { ExerciseDB() }
    }
    fun addExercise( roundId:Long): Exercise {
        return createExercise( roundId )
    }
    fun copyExercise ( exerciseId: Long) {
        val source = dataDao.getExerciseRel(exerciseId).toExercise()
        copyExercise(source, source.roundId)
    }
    fun deleteExercise ( exerciseId: Long) {
        val exercise = dataDao.getExerciseRel(exerciseId)
        exercise.speech?.let { dataDao.delSpeech(it.idSpeech) }
        exercise.sets?.forEach {
            val set = it.toSet()
            dataDao.delSpeech(set.speechId)
            dataDao.delSet(set.idSet)
        }
        dataDao.deleteExercise(exerciseId)
    }
    fun getExercises(roundId: Long): List<Exercise> = dataDao.getExercisesForRoundRel(roundId).map { it.toExercise() }
    fun updateExercise(exerciseDB: ExerciseDB): Int = dataDao.updateExercise(exerciseDB)

//Speech
    fun addSpeech(speech: SpeechDB): Long = dataDao.addSpeech(speech)
    fun updateSpeech(speech: SpeechDB): Int = dataDao.updateSpeech(speech)

//Activity
    fun getActivities(): List<ActivityDB> = dataDao.getActivities()
    fun setActivityToExercise(exerciseId: Long, activityId: Long): Exercise {
        dataDao.changeActivityExercise(exerciseId, activityId).toLong()
        return dataDao.getExerciseRel( exerciseId ).toExercise()
    }
    fun setColorActivity(activityId: Long, color: Int): ActivityDB{
        return dataDao.getActivity( dataDao.setColorActivity(activityId, color).toLong() )
    }
    fun addActivity(activity: Activity) = dataDao.addActivity( activity as ActivityDB )

    fun onUpdateActivity(activity: Activity) = dataDao.updateActivity( activity as ActivityDB )

//Sets
    fun addUpdateSet(exerciseId:Long, set: Set): Exercise{
        if (set.idSet < 1) { dataDao.addSet(set as SetDB) }
        else { dataDao.updateSet( set as SetDB) }
        return dataDao.getExerciseRel(exerciseId).toExercise()
    }
    fun copySet( setId: Long){
        copySet(dataDao.getSet(setId), 0)
    }
    fun deleteSet( setId: Long) {
         dataDao.delSet( setId )
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
                ExerciseDB(roundId = roundId, activityId = 1L, speechId = dataDao.addSpeech(SpeechDB())))
        )
    }
    fun copyTraining(id: Long): List<Training>{
        val training = dataDao.getTrainingRel(id).toTraining()
        val idNew = dataDao.addTraining( training.copy(
                idTraining = 0L, speechId = copySpeech(training.speech).idSpeech))
        training.rounds.forEach { round-> copyRound(round, idNew) }
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    private fun copyRound(round: Round, trainingId: Long = 0): Round
    {
        val idNew = dataDao.addRound((round as RoundDB).copy(
            idRound = 0,
            speechId = copySpeech(round.speech).idSpeech,
            trainingId = if (trainingId == 0L) round.trainingId else trainingId ))
        round.exercise.forEach { exercise-> copyExercise(exercise, idNew) }
        return dataDao.getRoundRel(idNew).toRound()
    }
    private fun copyExercise(exercise: Exercise, roundId: Long): Exercise
    {
        val idNew = dataDao.addExercise((exercise as ExerciseDB).copy(
            idExercise = 0L,
            speechId = copySpeech(exercise.speech).idSpeech,
            roundId = if (roundId == 0L) exercise.roundId else roundId ))
        exercise.sets.forEach { copySet( it, idNew) }
        return dataDao.getExerciseRel(idNew).toExercise()
    }
    private fun copySet(set: Set, exerciseId: Long): Set{
        val idNew = dataDao.addSet(
            (set as SetDB).copy(
                idSet = 0L,
                speechId = copySpeech(set.speech).idSpeech,
                exerciseId = if (exerciseId == 0L) set.exerciseId  else exerciseId
            )
        )
        return dataDao.getSetRel(idNew).toSet()
    }

    private fun copySpeech(speech: Speech): Speech{
        return dataDao.getSpeech(dataDao.addSpeech((speech as SpeechDB).copy(idSpeech = 0L)))
    }
}