package com.count_out.domain.entity.router.models

data class DataForNotification(
    val hours: String = "00",
    val minutes: String = "00",
    val seconds: String = "00",
    val heartRate: Int = 0,
    val enableLocation: Boolean = false
){

}
