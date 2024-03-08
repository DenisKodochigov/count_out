package com.example.count_out.entity

import com.example.count_out.navigation.TrainingsDestination

object Const {
    const val durationScreen = 800
    const val delayScreen = 200
    const val intervalDelay: Long = 100L
    const val durationChar = 67L
    val defaultScreen = TrainingsDestination.route

    const val ACTION_SERVICE_START = "ACTION_SERVICE_START"
    const val ACTION_SERVICE_STOP = "ACTION_SERVICE_STOP"
    const val ACTION_SERVICE_CANCEL = "ACTION_SERVICE_CANCEL"

    const val STOPWATCH_STATE = "STOPWATCH_STATE"
    const val ACTION = "ACTION"

    const val SET_CONTENT_TITLE = "COUNT_OUT"
    const val NOTIFICATION_ID = 999
    const val NOTIFICATION_CHANNEL_ID = "STOPWATCH_NOTIFICATION_ID"
    const val NOTIFICATION_CHANNEL_NAME = "STOPWATCH_NOTIFICATION"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "STOPWATCH_Channel_description"

    const val CLICK_REQUEST_CODE = 100
    const val CANCEL_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
    const val RESUME_REQUEST_CODE = 103
}