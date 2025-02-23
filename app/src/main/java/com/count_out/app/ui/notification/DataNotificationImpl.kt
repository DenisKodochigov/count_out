package com.count_out.app.ui.notification

import com.count_out.domain.entity.DataForNotification

data class DataNotificationImpl (
    override val hours: String = "00",
    override val minutes: String = "00",
    override val seconds: String = "00",
    override val heartRate: Int = 0,
    override val enableLocation: Boolean = false
): DataForNotification