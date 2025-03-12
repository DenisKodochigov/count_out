package com.count_out.presentation.screens.executor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.count_out.domain.entity.Coordinate
import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import com.count_out.presentation.models.SetImpl
import com.count_out.presentation.models.TickTimeImpl
import javax.inject.Singleton

@Singleton
data class ExecuteWorkoutScreenState(
    val training: Training? = null,

    val flowTime: TickTime = TickTimeImpl(hour = "00", min="00", sec= "00"),
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
    val updateSet: (Long, SetImpl)->Unit = { _, _->},
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



