package com.example.count_out.data.room

import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.CountDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TemporaryDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.data.room.tables.WorkoutDB
import com.example.count_out.entity.Const.MODE_DATABASE
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.speech.SpeechKit
import com.example.count_out.entity.workout.Activity
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.TemporaryBase
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataSource @Inject constructor(private val dataDao: DataDao) {
//Training
    fun getTraining(id: Long): TrainingDB {
        val training = dataDao.getTrainingRel(id).toTraining()
        return training
    }
    fun getTrainings(): List<Training> {
        if (MODE_DATABASE == 1 || MODE_DATABASE == 3) {
            dataDao.getTrainingsRel()
            Thread.sleep(1000)
        }
        return dataDao.getTrainingsRel().map { it.toTraining() }
    }
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
    private fun createTraining(): Long {
        val trainingId = dataDao.addTraining(
            TrainingDB(name = "Training", speechId = newSpeechKit()))
        createRound(trainingId, RoundType.UP)
        createRound(trainingId, RoundType.OUT)
        createRound(trainingId, RoundType.DOWN)
        return trainingId
    }
//Round
    fun getRound(roundId: Long): RoundDB = dataDao.getRoundRel(roundId).toRound()
    private fun createRound( trainingId: Long, typeRound: RoundType){
        dataDao.addRound(
            RoundDB(
                trainingId = trainingId,
                roundType = typeRound,
                speechId = newSpeechKit()
            )
        )
    }
    private fun copyRound(round: Round, trainingId: Long = 0): Round {
        val idNew = dataDao.addRound((round as RoundDB).copy(
            idRound = 0,
//            sequenceExercise = "",
            speechId = copySpeechKit(round.speech as SpeechKitDB),
            trainingId = if (trainingId == 0L) round.trainingId else trainingId ))
        round.exercise.forEach { exercise-> copyExercise(exercise, idNew) }
        return dataDao.getRoundRel(idNew).toRound()
    }
    private fun deleteRounds(trainingId: Long){
        val rounds = dataDao.getRoundsForTraining(trainingId)
        rounds.forEach {
            deleteExercises( it.idRound)
            deleteSpeechKit(it.speech)
            dataDao.delRound(it.idRound)
        }
    }
    fun changeSequenceExercise( roundId: Long, from: Pair<Long, Int> , to: Pair<Long, Int>){
        lg("0 from=${from}  to=${to}")
        lg("1 ${dataDao.getExercisesForRound(roundId).map { it.idExercise.toString() + "/" + it.idView }}")

        val listExercise = dataDao.getExercisesForRound(roundId)
        for ( id in from.first..to.first ){
            if (listExercise[id.toInt()].idView == 1){

            }
        }



        if (from.second > to.second){
            val id = from.second - 1
            dataDao.updateIdView1(roundId, from.second, to.second)
            while ( id > to.second && id > 0){

            }

            for (id in from.second..< to.second step -1){ dataDao.updateIdView1(roundId, id, (id + 1))
                lg("2-$id ${dataDao.getExercisesForRound(roundId).map { it.idExercise.toString() + "/" + it.idView }}") }
            dataDao.updateIdView1(roundId, from.second, to.second)
        } else {
            for (id in to.second..< from.second step -1){
                dataDao.updateIdView1(roundId, id, (id + 1))
                lg("2-$id ${dataDao.getExercisesForRound(roundId).map { it.idExercise.toString() + "/" + it.idView }}") }
            lg("3 ${dataDao.getExercisesForRound(roundId).map { it.idExercise.toString() + "/" + it.idView }}")
            dataDao.updateIdView1(roundId, from.second, to.second)
        }
    }
//Exercise
    fun getExercise( exerciseId:Long): Exercise {
        return if (exerciseId > 1) { dataDao.getExerciseRel(exerciseId).toExercise() }
                else { ExerciseDB() }
    }
    fun addExercise( roundId:Long): Exercise {
        return createExercise( roundId )
    }
    fun copyExercise ( exerciseId: Long) {
        val exercise = dataDao.getExerciseRel(exerciseId).toExercise()
        copyExercise(exercise, exercise.roundId)
    }
    private fun  deleteExercises(roundId: Long){
        val exercises = dataDao.getExercisesForRoundRel(roundId)
        exercises.forEach { exercise ->
            deleteSets(exercise.exerciseDB.idExercise)
            exercise.speechKit?.let { deleteSpeechKit(it.toSpeechKit()) }
            dataDao.deleteExercise(exercise.exerciseDB.idExercise)
        }
    }
    fun deleteExercise ( exerciseId: Long) {
        val exercise = dataDao.getExerciseRel(exerciseId).toExercise()
//        val round = getRound(exercise.roundId)
//        round.sequenceExercise = deleteIdFromSequence( round.sequenceExercise, exerciseId )
//        dataDao.updateRound(round)
        deleteSets( exerciseId )
        deleteSpeechKit(exercise.speech)
        dataDao.deleteExercise(exerciseId)
    }
    private fun createExercise( roundId: Long ): ExerciseDB {
        lg("idView = ${dataDao.getExerciseMaxSequential(roundId) + 1}")
        val exerciseId = dataDao.addExercise(
            ExerciseDB(
                roundId = roundId,
                idView = dataDao.getExerciseMaxSequential(roundId) + 1,
                activityId = 1L,
                speechId = newSpeechKit()))
        createSet( exerciseId )
        return dataDao.getExercise(exerciseId)
    }
    private fun copyExercise(exercise: Exercise, roundId: Long): Exercise {
        lg(" copyExercise idView=${dataDao.getExerciseMaxSequential(roundId) + 1}")
        val idNew = dataDao.addExercise((exercise as ExerciseDB).copy(
            idExercise = 0L,
            idView = dataDao.getExerciseMaxSequential(roundId) + 1,
            speechId = copySpeechKit(exercise.speech as SpeechKitDB),
            roundId = if (roundId == 0L) exercise.roundId else roundId ))
        exercise.sets.forEach { copySet( it, idNew) }
        val round  = dataDao.getRound(roundId)
//        if (round.sequenceExercise.contains(exercise.idExercise.toString())){
//            round.sequenceExercise = round.sequenceExercise.substringBefore("${exercise.idExercise}") +
//                    "${exercise.idExercise},$idNew" +
//                    round.sequenceExercise.substringAfter("${exercise.idExercise}")
//        } else {
//            if (round.sequenceExercise.isEmpty()) round.sequenceExercise += "$idNew"
//            else round.sequenceExercise += ",$idNew"
//        }
        dataDao.updateRound( round )
        return dataDao.getExerciseRel(idNew).toExercise()
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
    private fun createSet(exerciseId: Long): Long{
        return dataDao.addSet(
            SetDB(
                idSet = 0L,
                exerciseId = exerciseId,
                name = "Set " + "${dataDao.getSets(exerciseId).size + 1}",
                speechId = newSpeechKit()))
    }
    fun updateSet(set: Set){
        dataDao.updateSet( set as SetDB)
    }
    fun copySet( setId: Long){
        copySet(dataDao.getSetRel(setId).toSet(), 0)
    }
    private fun copySet(set: Set, exerciseId: Long): Set {
        val idNew = dataDao.addSet((set as SetDB).copy(
                idSet = 0L,
                speechId = copySpeechKit(set.speech as SpeechKitDB),
                exerciseId = if (exerciseId == 0L) set.exerciseId  else exerciseId
            )
        )
        return dataDao.getSetRel(idNew).toSet()
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
//SpeechKit
    private fun deleteSpeechKit(speechKit: SpeechKit){
        dataDao.delSpeech(speechKit.idBeforeStart)
        dataDao.delSpeech(speechKit.idAfterStart)
        dataDao.delSpeech(speechKit.idBeforeEnd)
        dataDao.delSpeech(speechKit.idAfterEnd)
        dataDao.delSpeechKit(speechKit.idSpeechKit)
    }
    fun updateSpeechKit(speechKit: SpeechKitDB): SpeechKit {
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
//Setting
    fun getSettings(): List<SettingDB> = dataDao.getSettings()
    fun getSetting(parameter: Int) = dataDao.getSetting(parameter)
    fun updateSetting(item: SettingDB) = dataDao.getSettingId(dataDao.updateSetting(item).toLong())
    //######################################################################################
    fun updateDuration(duration: Pair<Long, Long>){
        dataDao.updateDuration(id = duration.first, duration = duration.second)
    }
    //######################################################################################

    //######################################################################################
    fun writeTemporaryData(dataForBase: TemporaryBase) {
        dataDao.addRecordMetric(dataForBase as TemporaryDB)
    }
    fun clearTemporaryData() {
        dataDao.clearTemporaryData()
    }
    fun saveTraining(workout: WorkoutDB) {
        val step = 50
        val workoutId = dataDao.addWorkout(workout)
        val countRecord = dataDao.countTemporary()
        val listCount: MutableList<CountDB> = mutableListOf()

        for ( offset in 0..countRecord step step){
            dataDao.selectNRecord( step, offset).forEach { temporary->
                listCount.add(CountDB().add(workoutId,temporary))
            }
            dataDao.addCounts(listCount)
            listCount.clear()
        }
        clearTemporaryData()
    }
}


//    fun getNameRound(roundId: Long): String = dataDao.getNameRound(roundId)
//fun getNameTrainingFromRound(roundId: Long): String {
//    val trainingId = dataDao.getIdTrainingFormRound(roundId)
//    val trainingName = dataDao.getNameTraining(trainingId)
//    return "$trainingName:$trainingId"
//}
//    fun updateRound(round: RoundDB): Int = dataDao.updateRound(round)
//    fun updateTraining(trainingDB: TrainingDB): Int = dataDao.updateTraining(trainingDB)
//    private fun checkSequenceExercise(training: Training){
//        training.rounds.forEach { round->
//            if ( round.sequenceExercise.isEmpty()) {
//                val listExercise = dataDao.getExercisesForRoundRel(round.idRound)
//                (round as RoundDB).sequenceExercise = listExercise.map{ it.exerciseDB.idExercise}.toString()
//                round.sequenceExercise = round.sequenceExercise.replace("[", "").replace("]", "").replace(" ", "")
//                dataDao.updateRound(round)
//            }
//        }
//    }
//    fun changeSequenceExercise( roundId: Long, from: Int, to: Int){
//        val round = dataDao.getRound(roundId)
//        val listExercise = round.sequenceExercise.split(",")
//        val fromItem = listExercise[from]
//        val replaceItem = if (from == listExercise.size - 1) ",$fromItem" else "$fromItem,"
//
//        lg(" max sequential ${dataDao.getExerciseMaxSequential(roundId)}")
//        round.sequenceExercise = if (to >= listExercise.size - 1){
//            round.sequenceExercise.replace(replaceItem, "").let { "$it,$fromItem" }
//        } else {
//            val toItem = listExercise[to]
//            round.sequenceExercise.replace(replaceItem, "").let {
//                it.substringBefore(toItem) + "$fromItem,$toItem" + it.substringAfter(toItem)
//            }
//        }
//        dataDao.updateRound(round)
//    }
//Speech
//    fun addSpeech(speech: SpeechDB): Long = dataDao.addSpeech(speech)
//    fun updateSpeech(speech: SpeechDB): Int = dataDao.updateSpeech(speech)
//    fun addSetting(item: SettingDB): SettingDB{
//        return dataDao.getSettingId(dataDao.addSetting(item))
//    }
//    private fun deleteIdFromSequence(sequence: String, idExercise: Long ): String{
//        return if (sequence.contains("$idExercise,")){
//                    sequence.replace("$idExercise,", "")
//                } else if ( sequence.contains(", $idExercise")){
//                    sequence.replace(", $idExercise", "")
//                } else {  sequence }
//    }