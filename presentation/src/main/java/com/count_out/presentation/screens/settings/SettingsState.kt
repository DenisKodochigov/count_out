package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class SettingsState(
    val speechDescription: Boolean = true,
    val nameBle: String = "",
    val addressBle: String = "",
    val settings: Settings? = null,
    val heartRate: Int = 0,
    val lastConnectHearthRateDevice: DeviceBle? = null,
    val lastDevice: String? = null,
    val devicesUI: Map<String, DeviceBle> = emptyMap(),
    val scannedBle: Boolean = false,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,

    val activities: List<Activity> = emptyList(),
    val activityTmpl: Activity = ActivityImplP(1L),
    //for screen

    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val collapsing: Collapsing = Collapsing(),
    override val event: (Event) -> Unit,
    override var item: Domain? = null,
    override val nameSection: String = "",
    override var onDismiss: () -> Unit= {},
    override var onConfirmation: (Domain) -> Unit = {},
): BottomSheetInterface, DataState
