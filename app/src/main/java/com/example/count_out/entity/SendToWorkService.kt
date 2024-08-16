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
    fun getNextExercise() =
        if (indexExercise < (training.value?.rounds?.get(indexRound)?.exercise?.size ?: 0) -1)
            training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise + 1)
        else null
    fun lastSet() = indexSet == (training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.count() ?: 0) - 1
    fun getSet() = training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(indexSet)
    fun getSetIntervalReps(): Double = training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(indexSet)?.intervalReps ?: 0.0
}