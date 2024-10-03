package com.example.count_out.data.room

import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Const.MODE_DATABASE
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.SpeechKit
import com.example.count_out.entity.workout.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSource @Inject constructor(private val dataDao: DataDao) {
    fun getTraining(id: Long): TrainingDB {
        val training = dataDao.getTrainingRel(id).toTraining()
        checkSequenceExercise(training)
        return training
    }

    private fun checkSequenceExercise(training: Training){
        training.rounds.forEach { round->
            if ( round.sequenceExercise.isEmpty()) {
                val listExercise = dataDao.getExercisesForRoundRel(round.idRound)
                (round as RoundDB).sequenceExercise = listExercise.map{ it.exerciseDB.idExercise}.toString()
                round.sequenceExercise = round.sequenceExercise.replace("[", "").replace("]", "").replace(" ", "")
                dataDao.updateRound(round)
            }
        }
    }
    fun getTrainings(): List<Training> {
        if (MODE_DATABASE == 1) {
            dataDao.getTrainingsRel()
            Thread.sleep(800)
        }
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    fun updateTraining(trainingDB: TrainingDB): Int = dataDao.updateTraining(trainingDB)
    fun addTraining(): List<Training> {
        createTraining()
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    fun deleteTraining(id: Long): List<Training>{
        deleteRounds(id)
        dataDao.getTrainingRel(id).speechKit?.let { deleteSpeechKit(it.toSpeechKit()) }
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
            TrainingDB(name = "Training", speechId = newSpeechKit()))
        createRound(trainingId, RoundType.UP)
        createRound(trainingId, RoundType.OUT)
        createRound(trainingId, RoundType.DOWN)
        return trainingId
    }
//Round
    fun updateRound(round: RoundDB): Int = dataDao.updateRound(round)
    fun getNameRound(roundId: Long): String = dataDao.getNameRound(roundId)
    fun getRound(roundId: Long): RoundDB = dataDao.getRoundRel(roundId).toRound()
    fun changeSequenceExercise( roundId: Long, from: Int, to: Int){
        val round = dataDao.getRound(roundId)
        val listExercise = round.sequenceExercise.split(",")
        val fromItem = listExercise[from]
        val replaceItem = if (from == listExercise.size - 1) ",$fromItem" else "$fromItem,"

        round.sequenceExercise = if (to >= listExercise.size - 1){
            round.sequenceExercise.replace(replaceItem, "").let { "$it,$fromItem" }
        } else {
            val toItem = listExercise[to]
            round.sequenceExercise.replace(replaceItem, "").let {
                it.substringBefore(toItem) + "$fromItem,$toItem" + it.substringAfter(toItem)
            }
        }
        dataDao.updateRound(round)
    }
    private fun deleteRounds(trainingId: Long){
        val rounds = dataDao.getRoundsForTraining(trainingId)
        rounds.forEach {
            deleteExercises( it.idRound)
            deleteSpeechKit(it.speech)
            dataDao.delRound(it.idRound)
        }
    }
//Exercise

    fun getExercise( exerciseId:Long): Exercise {
        return if (exerciseId > 1) { dataDao.getExerciseRel(exerciseId).toExercise() }
                else { ExerciseDB() }
    }
    fun addExercise( roundId:Long, set: SetDB): Exercise {
        val exercise = createExercise( roundId , set)
        val round = getRound(roundId)
        round.sequenceExercise += ", ${exercise.idExercise}"
        dataDao.updateRound(round)
        return exercise
    }
    fun copyExercise ( exerciseId: Long) {
        val source = dataDao.getExerciseRel(exerciseId).toExercise()
        copyExercise(source, source.roundId)
    }
    fun deleteExercise ( exerciseId: Long) {
        val exercise = dataDao.getExerciseRel(exerciseId).toExercise()
        val round = getRound(exercise.roundId)

        round.sequenceExercise = deleteIdFromSequence( round.sequenceExercise, exerciseId )
        dataDao.updateRound(round)
        deleteSpeechKit(exercise.speech)
        exercise.sets.forEach {
            deleteSpeechKit(it.speech)
            dataDao.delSet(it.idSet)
        }
        dataDao.deleteExercise(exerciseId)
    }
    private fun  deleteExercises(roundId: Long){
        val exercises = dataDao.getExercisesForRoundRel(roundId)
        exercises.forEach { exercise ->
            deleteSets(exercise.exerciseDB.idExercise)
            exercise.speechKit?.let { deleteSpeechKit(it.toSpeechKit()) }
            deleteExercise(exercise.exerciseDB.idExercise)
        }
    }
//Speech
    fun addSpeech(speech: SpeechDB): Long = dataDao.addSpeech(speech)
    fun updateSpeech(speech: SpeechDB): Int = dataDao.updateSpeech(speech)
//SpeechKit
    private fun deleteSpeechKit(speechKit: SpeechKit){
        dataDao.delSpeech(speechKit.idBeforeStart)
        dataDao.delSpeech(speechKit.idAfterStart)
        dataDao.delSpeech(speechKit.idBeforeEnd)
        dataDao.delSpeech(speechKit.idAfterEnd)
        dataDao.delSpeechKit(speechKit.idSpeechKit)
    }
    fun updateSpeechKit(speechKit: SpeechKitDB): SpeechKit{
        speechKit.idBeforeStart = dataDao.updateSpeech(speechKit.beforeStart as SpeechDB).toLong()
        speechKit.idAfterStart = dataDao.updateSpeech(speechKit.afterStart as SpeechDB).toLong()
        speechKit.idBeforeEnd = dataDao.updateSpeech(speechKit.beforeEnd as SpeechDB).toLong()
        speechKit.idAfterEnd = dataDao.updateSpeech(speechKit.afterEnd as SpeechDB).toLong()
        return speechKit
    }
    fun addSpeechKit(speechKit: SpeechKitDB): Long{
        speechKit.idBeforeStart = dataDao.addSpeech(speechKit.beforeStart as SpeechDB)
        speechKit.idAfterStart = dataDao.addSpeech(speechKit.afterStart as SpeechDB)
        speechKit.idBeforeEnd = dataDao.addSpeech(speechKit.beforeEnd as SpeechDB)
        speechKit.idAfterEnd = dataDao.addSpeech(speechKit.afterEnd as SpeechDB)
        return dataDao.addSpeechKit(speechKit)
    }
    private fun newSpeechKit(): Long{
        val newSpeechKit = SpeechKitDB()
        newSpeechKit.idBeforeStart = dataDao.addSpeech(SpeechDB())
        newSpeechKit.idAfterStart = dataDao.addSpeech(SpeechDB())
        newSpeechKit.idBeforeEnd = dataDao.addSpeech(SpeechDB())
        newSpeechKit.idAfterEnd = dataDao.addSpeech(SpeechDB())
        return dataDao.addSpeechKit(newSpeechKit)
    }
    private fun copySpeechKit(speech: SpeechKitDB): Long{
        val newSpeechKit = speech.copy(idSpeechKit = 0L)
        newSpeechKit.idBeforeStart = dataDao.addSpeech(dataDao.getSpeech(speech.idBeforeStart).copy(idSpeech = 0L))
        newSpeechKit.idAfterStart = dataDao.addSpeech(dataDao.getSpeech(speech.idAfterStart).copy(idSpeech = 0L))
        newSpeechKit.idBeforeEnd = dataDao.addSpeech(dataDao.getSpeech(speech.idBeforeEnd).copy(idSpeech = 0L))
        newSpeechKit.idAfterEnd = dataDao.addSpeech(dataDao.getSpeech(speech.idAfterEnd).copy(idSpeech = 0L))
        return dataDao.addSpeechKit(newSpeechKit)
    }

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
    fun onDeleteActivity(activityId: Long) = dataDao.delActivity( activityId )

//Sets
    fun addUpdateSet(exerciseId:Long, set: Set): Exercise {
        if (set.idSet < 1) {
            (set as SetDB).speechId = newSpeechKit()
            dataDao.addSet(set) }
        else { dataDao.updateSet( set as SetDB) }
        return dataDao.getExerciseRel(exerciseId).toExercise()
    }
    fun updateSet(set: Set){
        dataDao.updateSet( set as SetDB)
    }
    fun copySet( setId: Long){
        copySet(dataDao.getSetRel(setId).toSet(), 0)
    }

    private fun deleteSets(exerciseId: Long){
        val sets = dataDao.getSets(exerciseId)
        sets.forEach { set->
            dataDao.delSet( set.idSet)
            deleteSpeechKit(set.speech)
        }
    }
    fun deleteSet( setId: Long) {
         dataDao.delSet( setId )
    }
//Setting
    fun getSettings(): List<SettingDB> = dataDao.getSettings()
    fun getSetting(parameter: Int) = dataDao.getSetting(parameter)
    fun updateSetting(item: SettingDB) = dataDao.getSettingId(dataDao.updateSetting(item).toLong())
    fun addSetting(item: SettingDB): SettingDB{
        return dataDao.getSettingId(dataDao.addSetting(item))
    }
    fun updateDuration(duration: Pair<Long, Long>){
        dataDao.updateDuration(id = duration.first, duration = duration.second)
    }
    //######################################################################################
    private fun createRound( trainingId: Long, typeRound: RoundType){
        dataDao.addRound(
            RoundDB(
                trainingId = trainingId,
                roundType = typeRound,
                speechId = newSpeechKit()
            )
        )
    }
    private fun createExercise( roundId: Long, set: SetDB): ExerciseDB {
        val exerciseId = dataDao.addExercise(
            ExerciseDB(
                roundId = roundId,
                activityId = 1L,
                speechId = newSpeechKit()))
        val nameSet = set.name + "${dataDao.getSets(exerciseId).size + 1}"
        dataDao.addSet(set.copy(name = nameSet, exerciseId = exerciseId))
        return dataDao.getExercise(exerciseId)
    }
    fun copyTraining(id: Long): List<Training>{
        val training = dataDao.getTrainingRel(id).toTraining()
        val idNew = dataDao.addTraining(
            training.copy(
                idTraining = 0L,
                name = training.name + " copy",
                amountActivity = training.amountActivity,
                speechId = copySpeechKit(training.speech as SpeechKitDB))
        )
        training.rounds.forEach { round-> copyRound(round, idNew) }
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
    private fun copyRound(round: Round, trainingId: Long = 0): Round
    {
        val idNew = dataDao.addRound((round as RoundDB).copy(
            idRound = 0,
            sequenceExercise = "",
            speechId = copySpeechKit(round.speech as SpeechKitDB),
            trainingId = if (trainingId == 0L) round.trainingId else trainingId ))
        round.exercise.forEach { exercise-> copyExercise(exercise, idNew) }
        return dataDao.getRoundRel(idNew).toRound()
    }

    private fun copyExercise(exercise: Exercise, roundId: Long): Exercise
    {
        val idNew = dataDao.addExercise((exercise as ExerciseDB).copy(
            idExercise = 0L,
            speechId = copySpeechKit(exercise.speech as SpeechKitDB),
            roundId = if (roundId == 0L) exercise.roundId else roundId ))
        exercise.sets.forEach { copySet( it, idNew) }
        val round  = dataDao.getRound(roundId)
        if (round.sequenceExercise.contains(exercise.idExercise.toString())){
            round.sequenceExercise = round.sequenceExercise.substringBefore("${exercise.idExercise}") +
                    "${exercise.idExercise},$idNew" +
                    round.sequenceExercise.substringAfter("${exercise.idExercise}")
        } else {
            if (round.sequenceExercise.isEmpty()) round.sequenceExercise += "$idNew"
            else round.sequenceExercise += ",$idNew"
        }
        dataDao.updateRound( round )
        return dataDao.getExerciseRel(idNew).toExercise()
    }
    private fun copySet(set: Set, exerciseId: Long): Set {
        val idNew = dataDao.addSet(
            (set as SetDB).copy(
                idSet = 0L,
                speechId = copySpeechKit(set.speech as SpeechKitDB),
                exerciseId = if (exerciseId == 0L) set.exerciseId  else exerciseId
            )
        )
        return dataDao.getSetRel(idNew).toSet()
    }

    private fun deleteIdFromSequence(sequence: String, idExercise: Long ): String{
        return if (sequence.contains("$idExercise,")){
                    sequence.replace("$idExercise,", "")
                } else if ( sequence.contains(", $idExercise")){
                    sequence.replace(", $idExercise", "")
                } else {  sequence }
    }
}