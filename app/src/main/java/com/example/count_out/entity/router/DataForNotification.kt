package com.example.count_out.entity.router

data class DataForNotification(
    val hours: String = "00",
    val minutes: String = "00",
    val seconds: String = "00",
    val heartRate: Int = 0,
    val enableLocation: Boolean = false
){

}
