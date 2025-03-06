package com.count_out.data.router

import com.count_out.domain.entity.bluetooth.BleConnection
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.workout.Training
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