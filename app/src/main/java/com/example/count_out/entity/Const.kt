package com.example.count_out.entity

import android.Manifest
import com.example.count_out.navigation.TrainingsDestination
import java.util.UUID

object Const {
    const val DATA_STORE_FILE_NAME = "ble_device.json"
    val DEFAULT_SCREEN = TrainingsDestination
    const val MODE_DATABASE = 1

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

    val permissions1 = listOf(
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_COARSE_LOCATION, ///
        Manifest.permission.ACCESS_FINE_LOCATION,   ///
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.VIBRATE,)
    val permissions2 = listOf(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.VIBRATE,)
    val serviceUUIDs = listOf(
        UUID.fromString("00001809-0000-0000-0000-000000000000"),
        UUID.fromString("00001810-0000-1000-8000-000000000000"),
        UUID.fromString("0000180D-0000-0000-0000-000000000000"),
        UUID.fromString("0000180E-0000-0000-0000-000000000000"),
        UUID.fromString("00001812-0000-0000-0000-000000000000"),
        UUID.fromString("00001814-0000-0000-0000-000000000000"),
        UUID.fromString("00001816-0000-0000-0000-000000000000"),
        UUID.fromString("00001818-0000-0000-0000-000000000000"),
        UUID.fromString("00001819-0000-0000-0000-000000000000"),
        UUID.fromString("00001822-0000-0000-0000-000000000000"),
        UUID.fromString("00001826-0000-0000-0000-000000000000"),
        UUID.fromString("0000183E-0000-0000-0000-000000000000"),
        UUID.fromString("00001840-0000-0000-0000-000000000000"),
    )
}