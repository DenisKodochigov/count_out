package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.LauncherBSp
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

    val launcherBS: LauncherBSp = LauncherBSp().list(emptyList()).type(null),
    val collapsing: Collapsing = Collapsing(),
    override var list: List<Domain> = emptyList(),
    override val event: (Event) -> Unit,
    override var item: Domain? = null,
    override var nameSection: String = "",
    override var onDismiss: () -> Unit= {},
    override var onConfirmation: (Domain) -> Unit = {},
): BottomSheetInterface
