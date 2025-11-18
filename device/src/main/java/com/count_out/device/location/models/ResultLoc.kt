package com.count_out.device.location.models

import com.count_out.data.models.entity.CoordinateDb
import com.count_out.domain.entity.enums.ConnectState


sealed class ResultLoc {
    data class Location(val value: CoordinateDb): ResultLoc()
    data class ConnectingStateBl(val connectState: ConnectState): ResultLoc()
    data object Nothing: ResultLoc()
    data class Error(val throwable: ThrowableLoc): ResultLoc()
}