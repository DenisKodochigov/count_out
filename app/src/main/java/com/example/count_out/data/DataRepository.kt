package com.example.count_out.data

import com.example.count_out.data.room.DataSource
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Plugins
import com.example.count_out.entity.Round
import com.example.count_out.entity.Set
import com.example.count_out.entity.SpeechKit
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
    fun setSpeech(trainingId: Long, speech: SpeechKit, item: Any?): Training
    {
        val speechId = if (speech.idSpeechKit == 0L) dataSource.addSpeechKit(speech as SpeechKitDB)
                        else dataSource.updateSpeechKit(speech as SpeechKitDB).idSpeechKit
        return when (item) {
            is Training -> {
                (item as TrainingDB).speechId = analiseId(idParent = item.speechId, idChild = speechId)
                dataSource.getTraining(item.idTraining)
            }
            is Round -> {
                (item as RoundDB).speechId = analiseId(idParent = item.speechId, idChild = speechId)
                dataSource.getTraining(trainingId)
            }
            is Exercise -> {
                (item as ExerciseDB).speechId = analiseId(idParent = item.speechId, idChild = speechId)
                dataSource.getTraining(trainingId)
            }
            is Set -> {
                (item as SetDB).speechId = analiseId(idParent = item.speechId, idChild = speechId)
                dataSource.getTraining(trainingId)
            }
            else -> { dataSource.getTraining(trainingId) }
        }
    }
    private fun analiseId(idParent: Long, idChild: Long): Long{
        return if (idParent == 0L) { idChild }
               else if (idParent != idChild) { idChild }
                else { idParent }
    }
    fun getExercise( roundId: Long, exerciseId:Long): Training {
        if ( exerciseId > 0) dataSource.getExercise( exerciseId )
        return dataSource.getTraining( dataSource.getRound(roundId = roundId).trainingId )
    }
    fun addExercise(trainingId: Long, roundId: Long, set: SetDB): Training {
        if ( roundId > 0) dataSource.addExercise( roundId, set )
        return dataSource.getTraining( trainingId )
    }
    fun copyExercise (trainingId: Long, exerciseId: Long): Training {
        if ( exerciseId > 0) dataSource.copyExercise( exerciseId)
        return getTraining(trainingId)
    }
    fun deleteExercise(trainingId: Long, exerciseId: Long): Training {
        if ( exerciseId > 0) dataSource.deleteExercise( exerciseId )
        return getTraining(trainingId)
    }
    fun changeSequenceExercise(trainingId: Long, roundId: Long, from: Int, to: Int): Training{
        if ( roundId > 0) dataSource.changeSequenceExercise( roundId, from, to)
        return getTraining(trainingId)
    }
    fun getNameTrainingFromRound(roundId: Long): String {
        return if (roundId > 0) dataSource.getNameTrainingFromRound(roundId) else ""
    }
    fun getNameRound(roundId: Long): String {
        return if (roundId > 0) dataSource.getNameRound(roundId) else ""
    }

//###### ACTIVITY ##################
    fun getActivities(): List<Activity> = dataSource.getActivities()
    fun setActivityToExercise(trainingId: Long, exerciseId: Long, activityId: Long): Training {
        if (exerciseId > 0 && activityId > 0)
            dataSource.setActivityToExercise(exerciseId = exerciseId, activityId = activityId)
        return getTraining( trainingId )
    }
    fun onSetColorActivity(activityId: Long, color: Int): ActivityDB {
        return if (activityId > 0 ) dataSource.setColorActivity(activityId, color)
                else  ActivityDB()
    }
    fun onSetColorActivityForSettings(activityId: Long, color: Int): List<Activity> {
        if (activityId > 0 ) dataSource.setColorActivity(activityId, color)
        return getActivities()
    }
    fun onAddActivity(activity: Activity): List<Activity> {
        dataSource.addActivity(activity)
        return getActivities()
    }
    fun onUpdateActivity(activity: Activity): List<Activity> {
        dataSource.onUpdateActivity(activity)
        return getActivities()
    }
    fun onDeleteActivity(activityId: Long): List<Activity>{
        dataSource.onDeleteActivity(activityId)
        return getActivities()
    }
    //###### SET ##################
    fun addUpdateSet(trainingId: Long, exerciseId:Long, set: Set): Training {
        if (exerciseId > 0 && set != SetDB()) dataSource.addUpdateSet(exerciseId, set).roundId
        return dataSource.getTraining(trainingId)
    }
    fun updateSet(trainingId: Long, set: SetDB): Training {
        dataSource.updateSet(set)
        return dataSource.getTraining(trainingId)
    }
    fun copySet(trainingId:Long, setId: Long): Training {
        if (setId > 0 ) dataSource.copySet( setId )
        return dataSource.getTraining(trainingId)
    }
    fun deleteSet(trainingId:Long, setId: Long): Training {
        if (setId > 0 ) dataSource.deleteSet( setId )
        return dataSource.getTraining(trainingId)
    }
//    fun onChangeSet(set: Set): Exercise = dataSource.addUpdateSet( set.exerciseId, set )

}