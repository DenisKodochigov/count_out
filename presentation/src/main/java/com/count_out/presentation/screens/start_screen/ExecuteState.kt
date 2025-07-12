package com.count_out.presentation.screens.start_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.count_out.domain.entity.Coordinate
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.models.SetImplP
import com.count_out.presentation.models.TickTimeImplP
import javax.inject.Singleton

@Singleton
data class ExecuteState(

    val flowTime: TickTime = TickTimeImplP(hour = "00", min="00", sec= "00"),
    val currentRest: Int = 0,
    val currentCount: Int = 0,
    val currentDuration: Int = 0,
    val currentDistance: Int = 0,
    val enableChangeInterval: Boolean = false,
    val stepTraining: StepPlan? = null,

    val heartRate: Int = 0,
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val bleConnectState: ConnectState = ConnectState.NOT_CONNECTED,

    val coordinate: Coordinate? = null,

    val showBottomSheetSaveTraining: MutableState<Boolean> = mutableStateOf(false),
    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val stateWorkOutService: RunningState = RunningState.Binding,
    val updateSet: (Long, SetImplP)->Unit = { _, _->},
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = { },
    val saveTraining: ()->Unit = { },
    val notSaveTraining: ()->Unit = { },
    @Stable var startTime: Long = 0L,

    @Stable var onDismissSaveTraining: (ExecuteState) -> Unit = { uiState ->
        uiState.showBottomSheetSaveTraining.value = false
        notSaveTraining()
    },
    @Stable var onConfirmASaveTraining: (ExecuteState) -> Unit = { uiState ->
        uiState.showBottomSheetSaveTraining.value = false
        saveTraining()
    },
)



