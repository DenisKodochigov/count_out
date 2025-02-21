package com.count_out.app.ui.modules

import com.count_out.app.device.bluetooth.modules.BleConnectionImpl
import com.count_out.domain.entity.Training
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.router.DataForServ
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForServImpl(
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
): DataForServ {
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
