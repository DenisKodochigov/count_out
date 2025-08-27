package com.count_out.device.bluetooth.models

import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.StateBleConnecting
import com.count_out.domain.entity.router.DeviceBle

sealed class ResultBle {
    data class HeartRate(val value: Int): ResultBle()
    data class ConnectingStateT(val connectState: ConnectState): ResultBle()
    data class StateConnectingBle(val value: StateBleConnecting): ResultBle()
    data class IntT(val value: Int): ResultBle()
    data class BooleanT(val value: Boolean): ResultBle()
    data class Device(val device: DeviceBle): ResultBle()
    data object Nothing: ResultBle()
    data class Error(val throwable: ThrowableBle): ResultBle()
}