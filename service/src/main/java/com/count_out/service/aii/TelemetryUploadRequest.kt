package com.count_out.service.aii

import com.count_out.device.network.TelemetryUploadDto

data class TelemetryUploadRequest(
    val items: List<TelemetryUploadDto>
)