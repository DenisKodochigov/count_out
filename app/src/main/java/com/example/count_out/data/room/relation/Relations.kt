package com.example.count_out.data.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set
import com.example.count_out.entity.SpeechKit

data class SpeechKitRel(
    @Embedded val speechKitDB: SpeechKitDB,
    @Relation (parentColumn = "idBeforeStart", entityColumn = "idSpeech", entity = SpeechDB::class) val beforeStart: SpeechDB?,
    @Relation (parentColumn = "idAfterStart", entityColumn = "idSpeech", entity = SpeechDB::class) val afterStart: SpeechDB?,
    @Relation (parentColumn = "idBeforeEnd", entityColumn = "idSpeech", entity = SpeechDB::class) val beforeEnd: SpeechDB?,
    @Relation (parentColumn = "idAfterEnd", entityColumn = "idSpeech", entity = SpeechDB::class) val afterEnd: SpeechDB?,
){
    fun toSpeechKit(): SpeechKit{
        return SpeechKitDB(
            idSpeechKit = speechKitDB.idSpeechKit,
            idBeforeStart = speechKitDB.idBeforeStart,
            idAfterStart = speechKitDB.idAfterStart,
            idBeforeEnd = speechKitDB.idBeforeEnd,
            idAfterEnd = speechKitDB.idAfterEnd,
            beforeStart = beforeStart ?: SpeechDB(),
            afterStart = afterStart ?: SpeechDB(),
            beforeEnd = beforeEnd ?: SpeechDB(),
            afterEnd = afterEnd ?: SpeechDB(),
        )
    }
}

data class SetRel(
    @Embedded val setDB: SetDB,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitDB::class) val speechKit: SpeechKitRel?
){
    fun toSet(): Set{
        return SetDB(
            idSet = setDB.idSet,
            name = setDB.name,
            exerciseId = setDB.exerciseId,
            speechId = setDB.speechId,
            speech = speechKit?.toSpeechKit() ?: SpeechKitDB(),
            weight = setDB.weight,
            intensity = setDB.intensity,
            distance = setDB.distance,
            duration = setDB.duration,
            reps = setDB.reps, // количество отстчетов
            intervalReps = setDB.intervalReps,
            intervalDown = setDB.intervalDown, //замедление отчетов
            groupCount = setDB.groupCount, // Группы отстчетов
            timeRest = setDB.timeRest,
            goal = setDB.goal,
        )
    }
}
data class ExerciseRel(
    @Embedded val exerciseDB: ExerciseDB,
    @Relation(parentColumn = "activityId", entityColumn = "idActivity", entity = ActivityDB::class) val activity: ActivityDB?,
    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SetDB::class) val sets: List<SetRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitDB::class) val speechKit: SpeechKitRel?
){
    fun toExercise(): Exercise{
        return ExerciseDB(
            idExercise = exerciseDB.idExercise,
            roundId = exerciseDB.roundId,
            activityId = exerciseDB.activityId,
            sequenceNumber = exerciseDB.sequenceNumber,
            activity = activity as Activity,
            speech = speechKit?.toSpeechKit() ?: SpeechKitDB(),
            speechId = exerciseDB.speechId,
            sets = sets?.map { it.toSet() } ?: emptyList()
        )
    }
}

data class NameTrainingRel(
    @Embedded val round: RoundDB,
    @Relation(parentColumn = "trainingId", entityColumn = "idTraining", entity = TrainingDB::class) val name: TrainingDB?,
)
data class RoundRel(
    @Embedded val round: RoundDB,
    @Relation(parentColumn = "idRound", entityColumn = "roundId", entity = ExerciseDB::class) val exercise: List<ExerciseRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitDB::class) val speechKit: SpeechKitRel?,
){
    fun toRound(): RoundDB{
        return RoundDB(
            exercise = sortExercise(round.sequenceExercise, exercise),
            idRound = round.idRound,
            roundType = round.roundType,
            speechId = round.speechId,
            speech = speechKit?.toSpeechKit() ?: SpeechKitDB(),
            trainingId = round.trainingId,
            sequenceExercise = round.sequenceExercise
        )
    }
    private fun sortExercise(sequenceExercise: String, exercise: List<ExerciseRel>? ):List<Exercise>{
        val sortingList = sequenceExercise.split(",")
        val listExercise: MutableList<Exercise> = mutableListOf()
        if (sortingList.size > 1) {
            sortingList.forEach { id->
                exercise?.find { it.exerciseDB.idExercise == id.toLong() }?.also { exercise ->
                    listExercise.add(exercise.toExercise()) } ?: mutableListOf<Exercise>()
            }
        } else {
            exercise?.forEach { listExercise.add( it.toExercise() ) } ?: mutableListOf<Exercise>()
        }
        return listExercise.toList()
    }
}
data class TrainingRel(
    @Embedded val training: TrainingDB,
    @Relation(parentColumn = "idTraining", entityColumn = "trainingId", entity = RoundDB::class) val rounds: List<RoundRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitDB::class) val speechKit: SpeechKitRel?,
){
    fun toTraining(): TrainingDB{
        return TrainingDB(
            idTraining = training.idTraining,
            isSelected = training.isSelected,
            name = training.name,
            rounds = rounds?.map { it.toRound() } ?: emptyList(),
            speech = speechKit?.toSpeechKit() ?: SpeechKitDB(),
            speechId = training.speechId
        )
    }
}