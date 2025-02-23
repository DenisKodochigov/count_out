package com.count_out.domain.entity

interface DataForNotification {
    val hours: String
    val minutes: String
    val seconds: String
    val heartRate: Int
    val enableLocation: Boolean
}