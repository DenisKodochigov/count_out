package com.example.count_out.entity.router

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
//        training = MutableStateFlow(null)
//        enableSpeechDescription = MutableStateFlow(true)
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
        return try {
            getRound()?.exercise?.get(indexExercise)
        } catch (e: Exception) {
            lg(" ERROR getExercise: $e")
            training.value?.let { trng ->
                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
                if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
                    indexExercise = trng.rounds[indexRound].exercise.count() - 1
            }
            training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)
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

    fun lastSet(): Boolean {
        return try {
            indexSet == (getExercise()?.sets?.count() ?: 0) - 1
        } catch (e: Exception) {
            lg(" ERROR lastSet: $e")
            false
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

    private fun getCurrentExercisesSize(): Int {
        return try {
            getRound()?.exercise?.count() ?: 0
        } catch (e: Exception) {
            lg(" ERROR getCurrentExercisesSize: $e")
            0
        }
    }

    fun getNextExercise(): Exercise? {
        return try {
            if (indexExercise < getCurrentExercisesSize() - 1)
                getRound()?.exercise?.get(indexExercise + 1)
            else getRound()?.exercise?.get(getCurrentExercisesSize() - 1)
        } catch (e: Exception) {
            lg(" ERROR getNextExercise: $e")
            getRound()?.exercise?.get(getCurrentExercisesSize() - 1)
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
}

