package com.example.count_out.entity

import android.Manifest
import com.example.count_out.navigation.TrainingsDestination
import java.util.UUID


object Const {
    const val DATA_STORE_FILE_NAME = "ble_device.json"
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

    const val MAX_TRIES = 4

    const val PULSE_RATE_MS = 1000L

    const val DELAY_CANCELED_COROUTINE = 100L

    const val START_REQUEST_CODE = 100
    const val PAUSE_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
    const val PERMISSION_REQUEST_CODE = 1
    val permissions1 = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.FOREGROUND_SERVICE,                     //with 28
        Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC,           //with 34
        Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE,    //with 34
        Manifest.permission.POST_NOTIFICATIONS,                     //with 33
        Manifest.permission.ACTIVITY_RECOGNITION,                   //with 29
        Manifest.permission.VIBRATE,
        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
        Manifest.permission.NFC,
        Manifest.permission.TRANSMIT_IR,
        Manifest.permission.UWB_RANGING,            //with 31
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_SCAN,         //with 31
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.BLUETOOTH_CONNECT,      //with 31
        Manifest.permission.BLUETOOTH_ADVERTISE,    //with 31
    )
    val permissions2 = listOf(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,//with 29
    )
    val serviceUUIDs = listOf(
//        UUID.fromString("00001809-0000-0000-0000-000000000000"),
//        UUID.fromString("00001810-0000-1000-8000-000000000000"),
        UUID.fromString("0000180A-0000-0000-0000-000000000000"),
        UUID.fromString("0000180D-0000-0000-0000-000000000000"),
        UUID.fromString("0000180F-0000-0000-0000-000000000000"),
//        UUID.fromString("00001812-0000-0000-0000-000000000000"),
//        UUID.fromString("00001814-0000-0000-0000-000000000000"),
//        UUID.fromString("00001816-0000-0000-0000-000000000000"),
//        UUID.fromString("00001818-0000-0000-0000-000000000000"),
//        UUID.fromString("00001819-0000-0000-0000-000000000000"),
//        UUID.fromString("00001822-0000-0000-0000-000000000000"),
//        UUID.fromString("00001826-0000-0000-0000-000000000000"),
//        UUID.fromString("0000183E-0000-0000-0000-000000000000"),
//        UUID.fromString("00001840-0000-0000-0000-000000000000"),
    )

    object UUIDBle {
        var attributes: HashMap<String, String> = HashMap()
        var DEVICE_INFORMATION_SERVICE =    "0000180a-0000-1000-8000-00805f9b34fb"
        var HEART_RATE_SERVICE =            "0000180d-0000-1000-8000-00805f9b34fb"
        var BATTERY_LEVEL_SERVICE =         "0000180f-0000-1000-8000-00805f9b34fb"
        var CLIENT_CHARACTERISTIC_CONFIG =  "00002902-0000-1000-8000-00805f9b34fb"
        var BATTERY_LEVEL =                 "00002a19-0000-1000-8000-00805f9b34fb"
        var MANUFACTURE_NAME =              "00002a29-0000-1000-8000-00805f9b34fb"
        var HEART_RATE_MEASUREMENT =        "00002a37-0000-1000-8000-00805f9b34fb"
        var IO_TANK =                       "19B10000-E8F2-537E-4F6C-D104768A1214"

        init {
            // Sample Services.
            attributes[DEVICE_INFORMATION_SERVICE] = "Device Information Service"
            attributes[HEART_RATE_SERVICE] = "Heart Rate Service"
            attributes[BATTERY_LEVEL_SERVICE] = "Battery level Service"
            attributes[CLIENT_CHARACTERISTIC_CONFIG] = "Client characteristic config"
            attributes[BATTERY_LEVEL] = "Battery level"
            attributes[MANUFACTURE_NAME] = "Manufacture name"
            attributes[HEART_RATE_MEASUREMENT] = "Heart Rate Measurement"
            attributes[IO_TANK] = "ioTank"
        }
        fun lookup(uuid: String, defaultName: String): String {
            val name = attributes.get(uuid)
            return name ?: defaultName
        }
    }
}