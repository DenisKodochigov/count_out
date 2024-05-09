package com.example.count_out.entity

import com.example.count_out.data.room.tables.TrainingDB
import kotlinx.coroutines.flow.MutableStateFlow

data class VariablesInService(
    var training: MutableStateFlow<Training> = MutableStateFlow(TrainingDB() as Training),
    var stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Stopped),
    var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    var indexRound: Int = 0,
    var indexExercise: Int = 0,
    var indexSet: Int = 0,
) {
    fun getTraining() = training.value
    fun getRound() = training.value.rounds[indexRound]
    fun getExercise() = training.value.rounds[indexRound].exercise[indexExercise]
    fun getNextExercise() =
        if (indexExercise < training.value.rounds[indexRound].exercise.size -1)
            training.value.rounds[indexRound].exercise[indexExercise + 1]
        else null
    fun lastSet() = indexSet == training.value.rounds[indexRound].exercise[indexExercise].sets.size - 1

    fun getSet() = training.value.rounds[indexRound].exercise[indexExercise].sets[indexSet]
    fun getSetIntervalReps() = training.value.rounds[indexRound].exercise[indexExercise].sets[indexSet].intervalReps
}