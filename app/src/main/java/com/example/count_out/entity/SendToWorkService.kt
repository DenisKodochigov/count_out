package com.example.count_out.entity

import kotlinx.coroutines.flow.MutableStateFlow

data class SendToWorkService(
    var training: MutableStateFlow<Training?> = MutableStateFlow(null),
    var stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Stopped),
    var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    var indexRound: Int = 0,
    var indexExercise: Int = 0,
    var indexSet: Int = 0,
) {
    fun getRound() = training.value?.rounds?.get(indexRound)
    fun getExercise() = training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)
    fun lastSet() = indexSet == (training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.count() ?: 0) - 1
    fun getSet(id: Int = indexSet) = training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(id)
    fun getSetIntervalReps(): Double = training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(indexSet)?.intervalReps ?: 0.0
    private fun getCurrentExercisesSize(): Int =
        training.value?.rounds?.get(indexRound)?.exercise?.count() ?: 0
    fun getNextExercise() =
        if (indexExercise < getCurrentExercisesSize() -1)
            training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise + 1)
        else null
    fun getNextSet(): Set? {
        var findingSet = false
        val currentIdSet = getSet()?.idSet
        training.value?.let { trainingIt->
            trainingIt.rounds.forEachIndexed { _, round ->
                round.exercise.forEachIndexed{ _, exercise ->
                    exercise.sets.forEachIndexed{ _, set ->
                        if (findingSet){ return set }
                        if (set.idSet == currentIdSet) findingSet = true
                    }
                }
            }
        }
        return null
    }
}