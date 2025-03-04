package com.count_out.app.presentation.models

import com.count_out.entity.entity.workout.Training
import com.count_out.entity.entity.bluetooth.BleConnection
import com.count_out.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForServ(
    var training: MutableStateFlow<Training?> = MutableStateFlow(null),
    var runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    val idSetChangeInterval: MutableStateFlow<Long> = MutableStateFlow(0),
    val interval: MutableStateFlow<Double> = MutableStateFlow(0.0),
    var indexRound: Int = 0,
    var indexExercise: Int = 0,
    var indexSet: Int = 0,
    var addressForSearch: String = "",
    var currentConnection: BleConnection? = null
){
    fun empty(){
        training = MutableStateFlow(null)
        runningState = MutableStateFlow(RunningState.Stopped)
        enableSpeechDescription = MutableStateFlow(true)
        indexRound = 0
        indexExercise = 0
        indexSet = 0
        addressForSearch = ""
        currentConnection = null
    }
}
