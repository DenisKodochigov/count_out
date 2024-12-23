package com.example.count_out.ui.screens.executor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.RunningState
import com.example.count_out.services.timer.models.TickTimeImpl
import com.example.count_out.entity.workout.Coordinate
import com.example.count_out.entity.workout.StepTraining
import com.example.count_out.entity.workout.Training
import javax.inject.Singleton

@Singleton
data class ExecuteWorkoutScreenState(
    val training: Training? = null,

    val flowTime: TickTimeImpl = TickTimeImpl(hour = "00", min="00", sec= "00"),
    val currentRest: Int = 0,
    val currentCount: Int = 0,
    val currentDuration: Int = 0,
    val currentDistance: Int = 0,
    val enableChangeInterval: Boolean = false,
    val stepTraining: StepTraining? = null,

    val heartRate: Int = 0,
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val bleConnectState: ConnectState = ConnectState.NOT_CONNECTED,

    val coordinate: Coordinate? = null,

    val showBottomSheetSaveTraining: MutableState<Boolean> = mutableStateOf(false),
    val stateWorkOutService: RunningState = RunningState.Binding,
    val updateSet: (Long, SetDB)->Unit = { _, _->},
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = { },
    val saveTraining: ()->Unit = { },
    val notSaveTraining: ()->Unit = { },
    @Stable var startTime: Long = 0L,

    @Stable var onDismissSaveTraining: (ExecuteWorkoutScreenState) -> Unit = { uiState ->
        uiState.showBottomSheetSaveTraining.value = false
        notSaveTraining()
    },
    @Stable var onConfirmASaveTraining: (ExecuteWorkoutScreenState) -> Unit = { uiState ->
        uiState.showBottomSheetSaveTraining.value = false
        saveTraining()
    },
)



