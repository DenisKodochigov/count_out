package com.count_out.app.presentation.models

import com.count_out.data.models.RunningState
import com.count_out.data.router.DataForServ
import com.count_out.domain.entity.Training
import com.count_out.domain.entity.bluetooth.BleConnection
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForServImpl(
    override var training: MutableStateFlow<Training?> = MutableStateFlow(null),
    override var runningState: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped),
    override var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    override val idSetChangeInterval: MutableStateFlow<Long> = MutableStateFlow(0),
    override val interval: MutableStateFlow<Double> = MutableStateFlow(0.0),
    override var indexRound: Int = 0,
    override var indexExercise: Int = 0,
    override var indexSet: Int = 0,
    override var addressForSearch: String = "",
    override var currentConnection: BleConnection? = null
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
