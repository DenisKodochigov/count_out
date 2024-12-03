package com.example.count_out.entity.workout

import com.example.count_out.R
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.speech.SpeechKit
import com.example.count_out.entity.ui.ExecuteInfoSet
import com.example.count_out.entity.ui.NextExercise

open class Training {
    open val idTraining: Long = 0L
    open val name: String = ""
    open val amountActivity: Int = 0
    open val rounds: List<Round> = emptyList()
    open val isSelected: Boolean = false
    open var speechId: Long = 0L
    open var speech: SpeechKit = SpeechKitDB()

    fun getNextExercise( exercise: Exercise?):NextExercise? {
        var finding = false
        val idExercise = exercise?.idExercise ?: 0L
        val list: MutableList<Pair<String, Int>> = mutableListOf()
        rounds.forEachIndexed { _, round ->
            round.exercise.forEachIndexed { _, exerciseL ->
                if (finding && exerciseL.sets.count() > 0) {
                    exerciseL.sets.forEachIndexed { _, set ->
                        when(set.goal){
                            GoalSet.DURATION -> list.add(
                                "${set.duration/(if(set.durationE == TimeE.SEC) 1 else 60)}" to set.durationE.id)
                            GoalSet.DISTANCE -> list.add(
                                "${set.distance/(if(set.distanceE == DistanceE.M) 1 else 1000)}" to set.distanceE.id)
                            GoalSet.COUNT -> list.add("${set.reps}" to R.string.rep)
                            GoalSet.COUNT_GROUP -> "" to 0
                        }
                    }
                    return NextExercise(
                        nextActivityName = exerciseL.activity.name,
                        nextExerciseId = exerciseL.idExercise,
                        nextExerciseSummarizeSet = list
                    )
                }
                if (exerciseL.idExercise == idExercise || idExercise == 0L) finding = true
            }
        }
        return null
    }
    fun getActivity( exercise: Exercise?): Activity? {
        val idExercise = exercise?.idExercise ?: 0L
        rounds.forEachIndexed { _, round ->
            round.exercise.forEach{ exerciseL ->
                if (exerciseL.idExercise == idExercise || idExercise == 0L) return exerciseL.activity
            }
        }
        return null
    }
    fun executeInfoSet( exercise: Exercise?): ExecuteInfoSet? {
        var finding = false
        val idExercise = exercise?.idExercise ?: 0L
        rounds.forEachIndexed { _, round ->
            round.exercise.forEachIndexed { _, exerciseL ->
                if (finding && exerciseL.sets.isNotEmpty()){
                    return ExecuteInfoSet(
                        currentSet = exerciseL.sets[0],
                        currentIndexSet = 0,
                        quantitySet = exerciseL.sets.count()
                    )
                }
                if (exerciseL.idExercise == idExercise || idExercise == 0L) {
                    finding = true
                    if (exerciseL.sets.isNotEmpty()){
                        return ExecuteInfoSet(
                            currentSet = exerciseL.sets[0],
                            currentIndexSet = 0,
                            quantitySet = exerciseL.sets.count()
                        )
                    }
                }
            }
        }
        return null
    }
}
