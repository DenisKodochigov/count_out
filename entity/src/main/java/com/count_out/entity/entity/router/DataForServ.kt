package com.count_out.entity.entity.router

import com.count_out.entity.entity.bluetooth.BleConnection
import com.count_out.entity.entity.workout.Training
import com.count_out.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

interface DataForServ {
    var training: MutableStateFlow<Training?>
    var runningState: MutableStateFlow<RunningState>
    var enableSpeechDescription: MutableStateFlow<Boolean>
    val idSetChangeInterval: MutableStateFlow<Long>
    val interval: MutableStateFlow<Double>
    var indexRound: Int
    var indexExercise: Int
    var indexSet: Int
    var addressForSearch: String
    var currentConnection: BleConnection?
}