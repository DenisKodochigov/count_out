package com.example.count_out.framework.room.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.count_out.entity.enums.Goal
import com.example.count_out.entity.enums.RoundType
import com.example.count_out.entity.enums.Units
import com.example.count_out.entity.enums.Zone
import com.example.count_out.framework.room.db.activity.ActivityTable
import com.example.count_out.framework.room.db.exercise.ExerciseTable
import com.example.count_out.framework.room.db.ring.RingTable
import com.example.count_out.framework.room.db.round.RoundTable
import com.example.count_out.framework.room.db.set.SetTable
import com.example.count_out.framework.room.db.speech.SpeechTable
import com.example.count_out.framework.room.db.speech_kit.SpeechKitTable
import com.example.count_out.framework.room.db.training.TrainingTable
import com.example.count_out.entity.models.ExerciseImpl
import com.example.count_out.entity.models.ParameterImpl
import com.example.count_out.entity.models.RingImpl
import com.example.count_out.entity.models.RoundImpl
import com.example.count_out.entity.models.SetImpl
import com.example.count_out.entity.models.SpeechKitImpl
import com.example.count_out.entity.models.TrainingImpl
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.ui.view_components.lg

data class SpeechKitRel(
    @Embedded val speechKitTable: SpeechKitTable,
    @Relation (parentColumn = "idBeforeStart", entityColumn = "idSpeech", entity = SpeechTable::class) val beforeStart: SpeechTable?,
    @Relation (parentColumn = "idAfterStart", entityColumn = "idSpeech", entity = SpeechTable::class) val afterStart: SpeechTable?,
    @Relation (parentColumn = "idBeforeEnd", entityColumn = "idSpeech", entity = SpeechTable::class) val beforeEnd: SpeechTable?,
    @Relation (parentColumn = "idAfterEnd", entityColumn = "idSpeech", entity = SpeechTable::class) val afterEnd: SpeechTable?,
){
    fun toSpeechKit(): SpeechKitImpl {
        return SpeechKitImpl(
            idSpeechKit = speechKitTable.idSpeechKit,
            idBeforeStart = speechKitTable.idBeforeStart,
            idAfterStart = speechKitTable.idAfterStart,
            idBeforeEnd = speechKitTable.idBeforeEnd,
            idAfterEnd = speechKitTable.idAfterEnd,
            beforeStart = beforeStart?.toSpeech(),
            afterStart = afterStart?.toSpeech(),
            beforeEnd = beforeEnd?.toSpeech(),
            afterEnd = afterEnd?.toSpeech(),
        )
    }
}

data class SetRel(
    @Embedded val setTable: SetTable,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRel?
){
    fun toSet(): SetImpl {
        return SetImpl(
            idSet = setTable.idSet,
            name = setTable.name,
            speechId = setTable.speechId,
            goal = Goal.entries[setTable.goal],
            exerciseId = setTable.exerciseId,
            reps = setTable.reps,
            duration = ParameterImpl(value = setTable.duration, Units.entries[setTable.durationU] ),
            distance = ParameterImpl(value = setTable.distance, Units.entries[setTable.distanceU] ),
            weight = ParameterImpl(value = setTable.weight, Units.entries[setTable.weightU] ),
            intervalReps = setTable.intervalReps,
            intensity = Zone.entries[setTable.intensity],
            intervalDown = setTable.intervalDown,
            groupCount = setTable.groupCount,
            rest = ParameterImpl(value = setTable.timeRest, Units.entries[setTable.timeRestU] ),
            speech = speechKit?.toSpeechKit(),
        )
    }
}
data class ExerciseRel(
    @Embedded val exerciseTable: ExerciseTable,
    @Relation(parentColumn = "activityId", entityColumn = "idActivity", entity = ActivityTable::class) val activity: ActivityTable?,
    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SetTable::class) val sets: List<SetRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRel?
){
    fun toExercise(): Exercise {
        return ExerciseImpl(
            idExercise = exerciseTable.idExercise,
            roundId = exerciseTable.roundId,
            ringId = exerciseTable.ringId,
            activityId = exerciseTable.activityId,
            idView = exerciseTable.idView,
            activity = activity?.toActivity(),
            speech = speechKit?.toSpeechKit(),
            speechId = exerciseTable.speechId,
            sets = sets?.map { it.toSet() } ?: emptyList(),
            amountSet = exerciseTable.amount,
            duration = ParameterImpl(value = exerciseTable.durationValue, unit = Units.entries[exerciseTable.durationUnit]),
        )
    }
}

data class NameTrainingRel(
    @Embedded val round: RoundTable,
    @Relation(parentColumn = "trainingId", entityColumn = "idTraining", entity = TrainingTable::class) val name: TrainingTable?,
)
data class RoundRel(
    @Embedded val round: RoundTable,
    @Relation(parentColumn = "idRound", entityColumn = "roundId", entity = ExerciseTable::class) val exercise: List<ExerciseRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRel?,
){
    fun toRound(): RoundImpl {
        return RoundImpl( exercise = exercise?.map { it.toExercise() } ?: emptyList(),  ///.sortedBy{ it.idView }.sortedBy{ it.idView } реализовать в usecase
            idRound = round.idRound,
            roundType = RoundType.entries[round.roundType],
            speechId = round.speechId,
            speech = speechKit?.toSpeechKit(),
            trainingId = round.trainingId,
            amount = round.amount,
            duration = ParameterImpl(value = round.durationValue, unit = Units.entries[round.durationUnit]),
        )
    }
}
data class RingRel(
    @Embedded val ring: RingTable,
    @Relation(parentColumn = "idRing", entityColumn = "ringId", entity = ExerciseTable::class) val exercise: List<ExerciseRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRel?,
){
    fun toRing(): RingImpl {
        return RingImpl(
            idRing = ring.idRing,
            trainingId = ring.trainingId,
            name = ring.name,
            countRing = ring.countRing,
            speechId = ring.speechId,
            speech = speechKit?.toSpeechKit(),
            exercise = exercise?.map { it.toExercise() } ?: emptyList(),
            amount = ring.amount,
            duration = ParameterImpl(value = ring.durationValue, unit = Units.entries[ring.durationUnit]),
        )
    }
}
data class TrainingRel(
    @Embedded val training: TrainingTable,
    @Relation(parentColumn = "idTraining", entityColumn = "trainingId", entity = RoundTable::class) val rounds: List<RoundRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRel?,
){
    fun toTraining(): TrainingImpl {
        var amountActivity = 0
        this.rounds?.forEach { round-> amountActivity += round.exercise?.count() ?:0 }
        return TrainingImpl(
            idTraining = training.idTraining,
            isSelected = training.isSelected,
            amountActivity = amountActivity,
            name = training.name,
            rounds = rounds?.map { it.toRound() } ?: emptyList(),
            speech = speechKit?.toSpeechKit(),
            speechId = training.speechId
        )
    }
}