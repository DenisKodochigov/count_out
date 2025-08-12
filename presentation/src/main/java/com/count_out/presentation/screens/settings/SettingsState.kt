package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Element
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.screens.prime.DataState
import com.count_out.presentation.screens.prime.Event

data class SettingsState(
    val settings: Settings? = null,
    val heartRate: Int = 0,
    val devicesUI: List<DeviceUI> = emptyList(),
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val activities: List<Activity> = emptyList(),
    val activityTmpl: Activity = ActivityImplP(1L),
    val scannedBle: Boolean = false,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,
    //for screen

    val showBS: ShowBottomSheet = ShowBottomSheet(),
    val collapsing: Collapsing = Collapsing(),
    override val event: (Event) -> Unit,
    override var item: Element? = null,
    override val nameSection: String = "",
    override var onDismiss: () -> Unit= {},
    override var onConfirmation: (Element, Element?) -> Unit = { _, _ ->},
): BottomSheetInterface, DataState
