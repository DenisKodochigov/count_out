package com.count_out.presentation.screens.execute

import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.location.Coordinate
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.models.TickTimeImplP
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
    val stepPlan: StepPlan? = null,
    val stateWorkOut: RunningState = RunningState.Binding,

    val heartRate: Int = 0,
    val lastConnectHearthRateDevice: DeviceBle? = null,
    val bleConnectState: ConnectState = ConnectState.NOT_CONNECTED,

    val coordinate: Coordinate? = null,
    val launcherBS: LauncherBSp = LauncherBSp(),

    var startTime: Long = 0L,

    var goToScreenPlans: ()->Unit = {},
    override var list: List<Domain> = emptyList(),
    override var nameSection: String = "",
    override var item: Domain? = null,
    override val devicesUI: List<DeviceBle> = emptyList(),
    override var onConfirmation: (Domain) -> Unit = {},
    override var onDismiss: () -> Unit = {},
    override val event: (Event) -> Unit ={},
): BottomSheetInterface



