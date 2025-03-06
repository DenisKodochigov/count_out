package com.example.count_out.ui.models

import com.example.count_out.data.bluetooth.modules.BleConnectionImpl
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.workout.Training
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
    var currentConnection: BleConnectionImpl? = null
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
