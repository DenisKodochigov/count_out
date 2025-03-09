package com.count_out.data.router.models

import com.count_out.domain.entity.bluetooth.BleConnection
import com.count_out.domain.entity.router.DataForServ
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForServImpl(
    override var training: MutableStateFlow<Training?>,
    override var runningState: MutableStateFlow<RunningState>,
    override var enableSpeechDescription: MutableStateFlow<Boolean>,
    override val idSetChangeInterval: MutableStateFlow<Long>,
    override val interval: MutableStateFlow<Double>,
    override var indexRound: Int,
    override var indexExercise: Int,
    override var indexSet: Int,
    override var addressForSearch: String,
    override var currentConnection: BleConnection?
): DataForServ
