package com.example.count_out.entity

import android.Manifest
import com.example.count_out.entity.bluetooth.UUIDBle
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

    const val MAX_TRIES = 4

    const val DELAY_CANCELED_COROUTINE = 100L

    const val START_REQUEST_CODE = 100
    const val PAUSE_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
    const val PERMISSION_REQUEST_CODE = 1
    val permissions1 = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, ///
        Manifest.permission.ACCESS_FINE_LOCATION,   ///
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC,
        Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.VIBRATE,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
        Manifest.permission.NFC,
        Manifest.permission.TRANSMIT_IR,
        Manifest.permission.UWB_RANGING,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_ADVERTISE,
    )
    val permissions2 = listOf(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    )
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
    const val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
    const val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"
    const val ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED"
    private const val STATE_DISCONNECTED = 0
    private const val STATE_CONNECTED = 2

    val UUID_HEART_RATE_MEASUREMENT: UUID = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT)
    val uuidHeartRate = UUIDBle(
        serviceUuid = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb"),
        charUuid = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb")
    )
    val uuidBatteryLevel = UUIDBle(
        serviceUuid = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb"),
        charUuid = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")
    )

    object SampleGattAttributes {
        private val attributes: HashMap<String?, String?> = HashMap<String?, String?>()
        var HEART_RATE_MEASUREMENT: String = "00002a37-0000-1000-8000-00805f9b34fb"
        init {
            attributes["0000180d-0000-1000-8000-00805f9b34fb"] = "Heart Rate Service"
            attributes[HEART_RATE_MEASUREMENT] = "Heart Rate Measurement"
        }
    }
}