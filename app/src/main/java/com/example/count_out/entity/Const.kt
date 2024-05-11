package com.example.count_out.entity

import com.example.count_out.navigation.TrainingsDestination

object Const {

    val DEFAULT_SCREEN = TrainingsDestination
    const val MODE_DATABASE = 2

    const val DURATION_SCREEN = 800
    const val DELAY_SCREEN = 200
    const val INTERVAL_DELAY: Long = 100L

    const val TAB_FADE_IN_ANIMATION_DURATION = 150
    const val TAB_FADE_IN_ANIMATION_DELAY = 100
    const val TAB_FADE_OUT_ANIMATION_DURATION = 100

    const val SET_CONTENT_TITLE = "COUNT_OUT"
    const val NOTIFICATION_ID = 999
    const val NOTIFICATION_EXTRA = "WORKOUT_NOTIFICATION_EXTRA"
    const val NOTIFICATION_CHANNEL_ID = "WORKOUT_NOTIFICATION_ID"
    const val NOTIFICATION_CHANNEL_NAME = "WORKOUT_NOTIFICATION"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "WORKOUT_CHANNEL_DESCRIPTION"

    const val START_REQUEST_CODE = 100
    const val PAUSE_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
}