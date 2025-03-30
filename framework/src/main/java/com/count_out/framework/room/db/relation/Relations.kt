package com.count_out.framework.room.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.ParameterImpl
import com.count_out.data.models.RingImpl
import com.count_out.data.models.RoundImpl
import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.framework.room.db.activity.ActivityTable
import com.count_out.framework.room.db.exercise.ExerciseTable
import com.count_out.framework.room.db.ring.RingTable
import com.count_out.framework.room.db.round.RoundTable
import com.count_out.framework.room.db.set.SetTable
import com.count_out.framework.room.db.speech.SpeechTable
import com.count_out.framework.room.db.speech_kit.SpeechKitTable
import com.count_out.framework.room.db.training.TrainingTable

data class SpeechKitRel(
    @Embedded val speechKitTable: SpeechKitTable,
    @Relation (parentColumn = "idBeforeStart", entityColumn = "idSpeech", entity = SpeechTable::class) val beforeStart: SpeechTable?,
    @Relation (parentColumn = "idAfterStart", entityColumn = "idSpeech", entity = SpeechTable::class) val afterStart: SpeechTable?,
    @Relation (parentColumn = "idBeforeEnd", entityColumn = "idSpeech", entity = SpeechTable::class) val beforeEnd: SpeechTable?,
    @Relation (parentColumn = "idAfterEnd", entityColumn = "idSpeech", entity = SpeechTable::class) val afterEnd: SpeechTable?,
){
    fun toSpeechKit(): SpeechKitImplD {
        return SpeechKitImplD(
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
    fun toSet(): SetImplD {
        return SetImplD(
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
    fun toExercise(): ExerciseImplD {
        return ExerciseImplD(
            idExercise = exerciseTable.idExercise,
            roundId = exerciseTable.roundId,
            ringId = exerciseTable.ringId,
            activityId = exerciseTable.activityId,
            idView = exerciseTable.idView,
            activity = activity?.toActivity(),
            speech = speechKit?.toSpeechKit(),
            speechId = exerciseTable.speechId,
            sets = sets?.map { it.toSet() } ?: emptyList(),
            amountSet = sets?.count() ?: 0,
            duration = sumSets(sets)
        )
    }
    fun sumSets(sets: List<SetRel>?): Int {
        var summ = 0
        sets?.let { item->
            item.forEach { set->
                summ = set.setTable.duration.toInt() * if (set.setTable.durationU == Units.H.ordinal) 3600
                else if (set.setTable.durationU == Units.M.ordinal) 60 else 1
                summ = set.setTable.timeRest.toInt() * if (set.setTable.timeRestU == Units.H.ordinal) 3600
                else if (set.setTable.timeRestU == Units.M.ordinal) 60 else 1
                summ += set.setTable.intervalReps.toInt() * set.setTable.reps
            }
        }
        return summ
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
        return RoundImpl(
            exercise = exercise?.map { it.toExercise() } ?: emptyList(),  ///.sortedBy{ it.idView }.sortedBy{ it.idView } реализовать в usecase
            idRound = round.idRound,
            roundType = RoundType.entries[round.roundType],
            speechId = round.speechId,
            speech = speechKit?.toSpeechKit(),
            trainingId = round.trainingId,
            amount = exercise?.count() ?: 0,
            duration = ParameterImpl(sumExercise(exercise), Units.M),
        )
    }
    fun sumExercise(exercises: List<ExerciseRel>?): Double {
        var summ = 0
        exercises?.let { items->
            items.forEach { exercise->
                exercise.sets?.forEach { set->
                    summ = set.setTable.duration.toInt() * if (set.setTable.durationU == Units.H.ordinal) 3600
                    else if (set.setTable.durationU == Units.M.ordinal) 60 else 1
                    summ = set.setTable.timeRest.toInt() * if (set.setTable.timeRestU == Units.H.ordinal) 3600
                    else if (set.setTable.timeRestU == Units.M.ordinal) 60 else 1
                    summ += set.setTable.intervalReps.toInt() * set.setTable.reps
                } ?: 0.0
            }
        }
        return summ/60.0
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
            amount = exercise?.count() ?: 0,
            duration = ParameterImpl(sumExercise(exercise), Units.M),  ///.sortedBy{ it.idView }.sortedBy{ it.idView } реализовать в usecase
        )
    }
    fun sumExercise(exercises: List<ExerciseRel>?): Double {
        var summ = 0
        exercises?.let { items->
            items.forEach { exercise->
                exercise.sets?.forEach { set->
                    summ = set.setTable.duration.toInt() * if (set.setTable.durationU == Units.H.ordinal) 3600
                    else if (set.setTable.durationU == Units.M.ordinal) 60 else 1
                    summ = set.setTable.timeRest.toInt() * if (set.setTable.timeRestU == Units.H.ordinal) 3600
                    else if (set.setTable.timeRestU == Units.M.ordinal) 60 else 1
                    summ += set.setTable.intervalReps.toInt() * set.setTable.reps
                } ?: 0.0
            }
        }
        return summ/60.0
    }
}
data class TrainingRel(
    @Embedded val training: TrainingTable,
    @Relation(parentColumn = "idTraining", entityColumn = "trainingId", entity = RoundTable::class) val rounds: List<RoundRel>?,
    @Relation(parentColumn = "idTraining", entityColumn = "trainingId", entity = RingTable::class) val rings: List<RingRel>?,
    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRel?,
){
    fun toTraining(): TrainingImplD {
        var amountActivity = 0
        this.rounds?.forEach { round-> amountActivity += round.exercise?.count() ?:0 }
        return TrainingImplD(
            idTraining = training.idTraining,
            isSelected = training.isSelected,
            amountActivity = amountActivity,
            name = training.name,
            rounds = rounds?.map { it.toRound() } ?: emptyList(),
            speech = speechKit?.toSpeechKit(),
            speechId = training.speechId,
            rings = rings?.map { it.toRing() } ?: emptyList()
        )
    }
}