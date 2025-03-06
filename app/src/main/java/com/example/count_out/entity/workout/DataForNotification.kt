package com.example.count_out.entity.workout

interface DataForNotification {
    val hours: String
    val minutes: String
    val seconds: String
    val heartRate: Int
    val enableLocation: Boolean
}