package com.example.count_out.data.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set

data class SetRel(
    @Embedded val setRel: SetDB,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeech", entity = SpeechDB::class) val speech: SpeechDB?
){
    fun toSet(): Set{
        return SetDB(
            idSet = setRel.idSet,
            name = setRel.name,
            exerciseId = setRel.exerciseId,
            speechId = speech?.idSpeech ?: 0L,
            speech = speech ?: SpeechDB(),
            weight = setRel.weight,
            intensity = setRel.intensity,
            distance = setRel.distance,
            duration = setRel.duration,
            reps = setRel.reps, // количество отстчетов
            intervalReps = setRel.intervalReps,
            intervalDown = setRel.intervalDown, //замедление отчетов
            groupCount = setRel.groupCount, // Группы отстчетов
            timeRest = setRel.timeRest,
            goal = setRel.goal,
        )
    }
}
data class ExerciseRel(
    @Embedded val exerciseDB: ExerciseDB,
    @Relation(parentColumn = "activityId", entityColumn = "idActivity", entity = ActivityDB::class) val activity: ActivityDB?,
    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SetDB::class) val sets: List<SetRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeech", entity = SpeechDB::class) val speech: SpeechDB?
){
    fun toExercise(): Exercise{
        return ExerciseDB(
            idExercise = exerciseDB.idExercise,
            roundId = exerciseDB.roundId,
            activityId = exerciseDB.activityId,
            sequenceNumber = exerciseDB.sequenceNumber,
            activity = activity as Activity,
            speech = speech ?: SpeechDB(),
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
    @Relation(
        parentColumn = "idRound",
        entityColumn = "roundId",
        entity = ExerciseDB::class) val exercise: List<ExerciseRel>?,
    @Relation(
        parentColumn = "speechId",
        entityColumn = "idSpeech",
        entity = SpeechDB::class) val speech: SpeechDB?
){
    fun toRound(): RoundDB{
        return RoundDB(
            exercise = exercise?.map { it.toExercise() } ?: emptyList(),
            idRound = round.idRound,
            roundType = round.roundType,
            speechId = round.speechId,
            speech = speech ?: SpeechDB(),
            trainingId = round.trainingId,
            sequenceExercise = round.sequenceExercise
        )
    }
}
data class TrainingRel(
    @Embedded val training: TrainingDB,
    @Relation(
        parentColumn = "idTraining",
        entityColumn = "trainingId",
        entity = RoundDB::class) val rounds: List<RoundRel>?,
    @Relation(
        parentColumn = "speechId",
        entityColumn = "idSpeech",
        entity = SpeechDB::class) val speech: SpeechDB?
){
    fun toTraining(): TrainingDB{
        return TrainingDB(
            idTraining = training.idTraining,
            isSelected = training.isSelected,
            name = training.name,
            rounds = rounds?.map { it.toRound() } ?: emptyList(),
            speech = speech ?: SpeechDB(),
            speechId = training.speechId
        )
    }
}