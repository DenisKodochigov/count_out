package com.example.count_out.data

import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Plugins
import com.example.count_out.entity.Round
import com.example.count_out.entity.Set
import com.example.count_out.entity.Speech
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(){

    fun getTrainings(): List<Training>{ return Plugins.listTr }
    fun getTraining(id: Long): Training { return Plugins.item(id) }
    fun changeNameTraining(id: Long): List<Training>{ return emptyList() }
    fun deleteTraining(id: Long): List<Training>{
        Plugins.listTr.remove(Plugins.listTr.find { it.idTraining ==id })
        return Plugins.listTr
    }
    fun copyTraining(id: Long): List<Training>
    {
        val item = Plugins.listTr.find { it.idTraining ==id }
        val idLast = Plugins.listTr.maxBy { it.idTraining }.idTraining

        item?.let {
            val itemC = item.copy()
            itemC.idTraining = idLast + 1
            Plugins.listTr.add(itemC) }
        return Plugins.listTr
    }

    fun changeNameTraining(id: Long, name: String): Training{
        val item = Plugins.listTr.find { it.idTraining ==id }
        item?.let { it.name = name } ?: TrainingDB()
        log(true, "changeNameTraining: $name")
        return item as Training
    }
    fun addTraining(name: String): List<Training>{ return emptyList() }
    fun deleteTrainingNothing(id: Long){
        Plugins.listTr.remove(Plugins.listTr.find { it.idTraining == id })
    }
    fun setSpeech(speech: Speech, item: Any?): Training{
        var training: TrainingDB? = null
        when (item) {
            is Training -> {
                training = Plugins.listTr.find { it.idTraining == item.idTraining }
                if (training != null) {
                    training.speech = checkExistingSpeech(training.speech.idSpeech, speech)
                    training.speechId = training.speech.idSpeech
                }
            }
            is Exercise -> {
                var exercise: ExerciseDB? = null
                Plugins.listTr.forEach lit@ { tr->
                    tr.rounds.forEach {round ->
                        round.exercise.forEach { exer ->
                            if (exer.idExercise == item.idExercise) {
                                training = tr
                                exercise = exer as ExerciseDB
                                return@lit
                            }
                        }
                    }
                }
                if (exercise != null) {
                    exercise!!.speech = checkExistingSpeech(exercise!!.speech.idSpeech, speech)
                    exercise!!.speechId = exercise!!.speech.idSpeech
                }
            }
            is Round -> {
                var round:RoundDB? = null
                Plugins.listTr.forEach lit@ { tr->
                    tr.rounds.forEach { rnd ->
                        if (rnd.idRound == item.idRound){
                            training = tr
                            round = rnd as RoundDB
                            return@lit
                        }
                    }
                }
                if (round != null) {
                    val speechPlug = checkExistingSpeech(round!!.speech.idSpeech, speech)
                    round!!.speech = speechPlug
                    round!!.speechId = speechPlug.idSpeech
                }
            }
            else -> null
    }
    return training ?: TrainingDB()
}

    private fun checkExistingSpeech(id: Long, enterSpeech: Speech): Speech {
        val speechLoc = if (id > 0) {
            Plugins.listSpeech.find { it.idSpeech == id } as Speech
        }
        else {
            Plugins.listSpeech.add(SpeechDB(idSpeech = (Plugins.listSpeech.size + 1).toLong()))
            Plugins.listSpeech.last()
        }
        speechLoc.soundAfterEnd = enterSpeech.soundAfterEnd
        speechLoc.soundAfterStart = enterSpeech.soundAfterStart
        speechLoc.soundBeforeEnd = enterSpeech.soundBeforeEnd
        speechLoc.soundBeforeStart = enterSpeech.soundBeforeStart
        return speechLoc
    }
    fun getExercise(roundId: Long, exerciseId:Long): Exercise {
        var exercise = ExerciseDB()
        val round = Plugins.listRound.find { it.idRound == roundId }
        if (round != null){
            if (round.exercise.size == 0){
                Plugins.listEx.add(exercise)
                round.exercise.add(Plugins.listEx.last())
            } else {
                val exerciseNull = Plugins.listEx.find { it.idExercise == exerciseId }
                if (exerciseNull != null) exercise = exerciseNull
            }
        }
        return exercise
    }

    fun getNameTraining(roundId: Long): String {
        var training = TrainingDB()
        Plugins.listTr.forEach { tr->
            val round = tr.rounds.find { it.idRound == roundId }
            if (round != null) {
                training = tr
            }
        }
        return training.name
    }
    fun getNameRound(roundId: Long): String {
        val round = Plugins.listRound.find { it.idRound == roundId } ?: RoundDB()
        return round.roundType.toString()
    }
    //###### ACTIVITY ##################
    fun getActivities(): List<Activity>{
        return Plugins.listActivity
    }
    fun setActivityToExercise(exerciseId: Long, activityId: Long): Exercise {
        val exerise = Plugins.listEx.find { it.idExercise == exerciseId } ?: ExerciseDB()
        val activity = Plugins.listActivity.find { it.idActivity == activityId } ?: ActivityDB()
        exerise.activity = activity
        exerise.activityId = activityId
        return exerise
    }

    fun onSetColorActivity(activityId: Long, color: Int) {
        val activity: ActivityDB = Plugins.listActivity.find { it.idActivity == activityId } as ActivityDB
        activity.color = color
    }
    //###### SET ##################
    fun addUpdateSet(exerciseId:Long, set: Set): Exercise{

        val setMy = if (set.idSet == 0L && Plugins.listSets.isEmpty()) {
                       Plugins.listSets.add(set)
                       Plugins.listSets.last() as SetDB
                    } else if (set.idSet == 0L && Plugins.listSets.isNotEmpty()){
                        val index = Plugins.listSets.lastIndex.toLong() + 1
                        (set as SetDB).idSet = index
                        Plugins.listSets.add(set)
                        Plugins.listSets.last() as SetDB
                    } else {
                        Plugins.listSets.find { it.idSet == set.idSet } as SetDB
                    }
        setMy.speech = set.speech
        setMy.name = set.name
        setMy.exerciseId = exerciseId
        setMy.distance = set.distance
        setMy.duration = set.duration
        setMy.reps = set.reps
        setMy.weight = set.weight
        setMy.intervalReps = set.intervalReps
        setMy.speechId = set.speechId
        setMy.intensity = set.intensity
        setMy.intervalDown = set.intervalDown
        setMy.groupCount = set.groupCount
        setMy.timeRest = set.timeRest

        val exercise = Plugins.listEx.find { it.idExercise == exerciseId } as ExerciseDB
        exercise.sets.add(setMy as Set)

        return exercise
    }
    fun getSets(roundId: Long, exerciseId:Long): List<Set> {
        return emptyList()
    }

}