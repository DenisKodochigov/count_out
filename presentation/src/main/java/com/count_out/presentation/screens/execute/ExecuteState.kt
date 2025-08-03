package com.count_out.presentation.screens.execute

import androidx.compose.runtime.Stable
import com.count_out.domain.entity.Coordinate
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.models.TickTimeImplP
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event
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
    val stateWorkOut: RunningState = RunningState.Binding,

    val heartRate: Int = 0,
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val bleConnectState: ConnectState = ConnectState.NOT_CONNECTED,

    val coordinate: Coordinate? = null,

    val showBS: ShowBottomSheet = ShowBottomSheet(element = TrainingImplP()),

    @Stable var startTime: Long = 0L,
    override val event: (Event) -> Unit,
): DataState



