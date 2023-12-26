package com.example.count_out.data

import com.example.count_out.data.room.DataSource
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(private val dataSource: DataSource){

    fun getTrainings(): List<Training> = dataSource.getTrainings()
    fun getTraining(id: Long): Training = dataSource.getTraining(id)
    fun deleteTraining(id: Long): List<Training> = dataSource.deleteTraining(id)
    fun copyTraining(id: Long): List<Training> = dataSource.copyTraining(id)
    fun changeNameTraining(training: Training, name: String): Training = dataSource.changeNameTraining(training, name)

    fun addTraining(name: String): List<Training>{ return emptyList() }
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
            else -> null
    }
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