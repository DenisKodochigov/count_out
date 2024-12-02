package com.example.count_out.entity.router

import com.example.count_out.R
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForWork (
    var training: MutableStateFlow<Training?> = MutableStateFlow(null),
    var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    var indexRound: Int = 0,
    var indexExercise: Int = 0,
    var indexSet: Int = 0,
    var cancelCoroutineWork: ()-> Unit = {},
){
    fun empty(){
        indexRound = 0
        indexExercise = 0
        indexSet = 0
    }

    fun getRound(): Round? {
        return try {
            training.value?.rounds?.get(indexRound)
        } catch (e: Exception) {
            lg(" ERROR getRound: $e")
            training.value?.let { trng ->
                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
            }
            training.value?.rounds?.get(indexRound)
        }
    }

    fun getExercise(): Exercise? {
        return getRound()?.exercise?.let {
            if (it.isNotEmpty()) {
                try { getRound()?.exercise?.get(indexExercise) }
                catch (e: Exception) {
                    lg(" ERROR getExercise: $e")
                    training.value?.let { trng ->
                        if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
                        if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
                            indexExercise = trng.rounds[indexRound].exercise.count() - 1
                    }
                    training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)
                }
            } else null
        }
    }

    fun getSet(ind: Int = indexSet): Set? {
        var indSet = ind
        return try {
            getExercise()?.sets?.get(ind)
        } catch (e: Exception) {
            lg(" ERROR getSet: $e")
            training.value?.let { trng ->
                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
                if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
                    indexExercise = trng.rounds[indexRound].exercise.count() - 1
                if (trng.rounds[indexRound].exercise[indexExercise].sets.count() - 1 < indSet)
                    indSet = trng.rounds[indexRound].exercise[indexExercise].sets.count() - 1
            }
            training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(indSet)
        }
    }
    fun getSetIntervalReps(): Double {
        return try {
            getSet()?.intervalReps ?: 0.0
        } catch (e: Exception) {
            lg(" ERROR getSetIntervalReps: $e")
            0.0
        }
    }
    fun getNextSet(): Set? {
        var findingSet = false
        val currentIdSet = getSet()?.idSet

        training.value?.let { trainingIt ->
            trainingIt.rounds.forEachIndexed { _, round ->
                round.exercise.forEachIndexed { _, exercise ->
                    exercise.sets.forEachIndexed { _, set ->
                        if (findingSet) {
                            return set
                        }
                        if (set.idSet == currentIdSet) findingSet = true
                    }
                }
            }
        }
        return null
    }
    fun getNextExercise(): Exercise? {
        var finding = false
        val idExercise = getExercise()?.idExercise

        training.value?.let { trainingIt ->
            trainingIt.rounds.forEachIndexed { _, round ->
                round.exercise.forEachIndexed { _, exercise ->
                    if (finding) { return exercise }
                    if (exercise.idExercise == idExercise) finding = true
                }
            }
        }
        return null
    }
    fun getSummarizeSet(): List<Pair<String, Int>>{
        var finding = false
        val idExercise = getExercise()?.idExercise
        val list: MutableList<Pair<String, Int>> = mutableListOf()
        training.value?.let { trainingIt ->
            trainingIt.rounds.forEachIndexed { _, round ->
                round.exercise.forEachIndexed { _, exercise ->
                    if (finding) {
                        exercise.sets.forEachIndexed { _, set ->
                            when(set.goal){
                                GoalSet.DURATION -> list.add(
                                    "${set.duration/(if(set.durationE == TimeE.SEC) 1 else 60)}" to set.durationE.id)
                                GoalSet.DISTANCE -> list.add(
                                    "${set.distance/(if(set.distanceE == DistanceE.M) 1 else 1000)}" to set.distanceE.id)
                                GoalSet.COUNT -> list.add("${set.reps}" to R.string.rep)
                                GoalSet.COUNT_GROUP -> "" to 0
                            }
                        }
                        return list
                    }
                    if (exercise.idExercise == idExercise) finding = true
                }
            }
        }
        return list
    }
}

