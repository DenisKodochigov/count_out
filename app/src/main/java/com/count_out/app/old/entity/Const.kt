package com.count_out.app.old.entity

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.count_out.app.presentation.navigation.TrainingsDestination

//
//object Const {
//    const val DATA_STORE_FILE_NAME = "ble_device.json"
    val DEFAULT_SCREEN = TrainingsDestination
//    const val MODE_DATABASE = 2
//
    const val DURATION_SCREEN = 800
    const val DELAY_SCREEN = 200
//
    const val TAB_FADE_IN_ANIMATION_DURATION = 150
    const val TAB_FADE_IN_ANIMATION_DELAY = 100
    const val TAB_FADE_OUT_ANIMATION_DURATION = 100
//
    const val SET_CONTENT_TITLE = "COUNT_OUT"
    const val NOTIFICATION_ID = 999
    const val NOTIFICATION_EXTRA = "WORKOUT_NOTIFICATION_EXTRA"
    const val NOTIFICATION_CHANNEL_ID = "WORKOUT_NOTIFICATION_ID"
    const val NOTIFICATION_CHANNEL_NAME = "WORKOUT_NOTIFICATION"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "WORKOUT_CHANNEL_DESCRIPTION"
//
    const val START_REQUEST_CODE = 100
    const val PAUSE_REQUEST_CODE = 101
    const val STOP_REQUEST_CODE = 102
//    val DEFAULT_SCREEN = TrainingsDestination
//
//    val permissions1 = listOf(
//        ACCESS_COARSE_LOCATION,
//        ACCESS_FINE_LOCATION,
//        INTERNET,
//        ACCESS_NETWORK_STATE,
//        FOREGROUND_SERVICE,                     //with 28
//        FOREGROUND_SERVICE_DATA_SYNC,           //with 34
//        FOREGROUND_SERVICE_CONNECTED_DEVICE,    //with 34
//        POST_NOTIFICATIONS,                     //with 33
//        ACTIVITY_RECOGNITION,                   //with 29
//        VIBRATE,
//        REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
//        CHANGE_NETWORK_STATE,
//        CHANGE_WIFI_STATE,
//        CHANGE_WIFI_MULTICAST_STATE,
//        NFC,
//        TRANSMIT_IR,
//        UWB_RANGING,                             //with 31
//        BLUETOOTH,
//        BLUETOOTH_SCAN,                          //with 31
//        BLUETOOTH_ADMIN,
//        BLUETOOTH_CONNECT,                      //with 31
//        BLUETOOTH_ADVERTISE,                    //with 31
//    )
//    val permissions2 = listOf(
//        ACCESS_BACKGROUND_LOCATION,//with 29
//    )
    private val dp0 = 0.dp
    private val dp1 = 1.dp
    private val dp2 = 2.dp
    val contourAll1 = PaddingValues(top = dp1, bottom = dp1, start = dp1, end = dp1)
    val contourBot1 = PaddingValues(top = dp0, bottom = dp1, start = dp0, end = dp0)
    val contourAll2 = PaddingValues(top = dp2, bottom = dp2, start = dp2, end = dp2)
    val contourHor2 = PaddingValues(top = dp2, bottom = dp2, start = dp0, end = dp0)
//
//    val serviceUUIDs = listOf(
////        UUID.fromString("00001809-0000-0000-0000-000000000000"),
////        UUID.fromString("00001810-0000-1000-8000-000000000000"),
//        UUID.fromString("0000180A-0000-0000-0000-000000000000"),
//        UUID.fromString("0000180D-0000-0000-0000-000000000000"),
//        UUID.fromString("0000180F-0000-0000-0000-000000000000"),
////        UUID.fromString("00001812-0000-0000-0000-000000000000"),
////        UUID.fromString("00001814-0000-0000-0000-000000000000"),
////        UUID.fromString("00001816-0000-0000-0000-000000000000"),
////        UUID.fromString("00001818-0000-0000-0000-000000000000"),
////        UUID.fromString("00001819-0000-0000-0000-000000000000"),
////        UUID.fromString("00001822-0000-0000-0000-000000000000"),
////        UUID.fromString("00001826-0000-0000-0000-000000000000"),
////        UUID.fromString("0000183E-0000-0000-0000-000000000000"),
////        UUID.fromString("00001840-0000-0000-0000-000000000000"),
//    )
//
//    object UUIDBle {
//        var attributes: HashMap<String, String> = HashMap()
//        var DEVICE_INFORMATION_SERVICE =    "0000180a-0000-1000-8000-00805f9b34fb"
//        var HEART_RATE_SERVICE =            "0000180d-0000-1000-8000-00805f9b34fb"
//        var BATTERY_LEVEL_SERVICE =         "0000180f-0000-1000-8000-00805f9b34fb"
//        var CLIENT_CHARACTERISTIC_CONFIG =  "00002902-0000-1000-8000-00805f9b34fb"
//        var BATTERY_LEVEL =                 "00002a19-0000-1000-8000-00805f9b34fb"
//        var MANUFACTURE_NAME =              "00002a29-0000-1000-8000-00805f9b34fb"
//        var HEART_RATE_MEASUREMENT =        "00002a37-0000-1000-8000-00805f9b34fb"
//        var IO_TANK =                       "19B10000-E8F2-537E-4F6C-D104768A1214"
//
//        init {
//            // Sample Services.
//            attributes[DEVICE_INFORMATION_SERVICE] = "Device Information Service"
//            attributes[HEART_RATE_SERVICE] = "Heart Rate Service"
//            attributes[BATTERY_LEVEL_SERVICE] = "Battery level Service"
//            attributes[CLIENT_CHARACTERISTIC_CONFIG] = "Client characteristic config"
//            attributes[BATTERY_LEVEL] = "Battery level"
//            attributes[MANUFACTURE_NAME] = "Manufacture name"
//            attributes[HEART_RATE_MEASUREMENT] = "Heart Rate Measurement"
//            attributes[IO_TANK] = "ioTank"
//        }
//    }
//}
//val weatherCod: HashMap<Int, Int> = hashMapOf(
//    0 to R.string.clear_sky,
//    1 to R.string.mainly_clear,
//    2 to R.string.partly_cloudy,
//    3 to R.string.overcast,
//    45 to R.string.fog,
//    48 to R.string.depositing_rime_fog,
//    51 to R.string.drizzle_light,
//    53 to R.string.drizzle_moderate,
//    55 to R.string.drizzle_dense,
//    56 to R.string.freezing_drizzle_light,
//    57 to R.string.freezing_drizzle_dense,
//    61 to R.string.rain_slight,
//    63 to R.string.rain_moderate,
//    65 to R.string.rain_heavy,
//    66 to R.string.freezing_rain_light,
//    67 to R.string.freezing_rain_heavy,
//    71 to R.string.snow_fall_slight,
//    73 to R.string.snow_fall_moderate,
//    75 to R.string.snow_fall_heavy,
//    77 to R.string.snow_grains,
//    80 to R.string.rain_showers_slight,
//    81 to R.string.rain_showers_moderate,
//    82 to R.string.rain_showers_violent,
//    85 to R.string.snow_showers_slight,
//    86 to R.string.snow_showers_heavy,
//    95 to R.string.thunderstorm,
//    96 to R.string.thunderstorm_with_slight_hail,
//    99 to R.string.thunderstorm_with_heavy_hail,
//)
//val weatherCodIcon: HashMap<Int, Int> = hashMapOf(
//    0 to R.drawable.frame_20,
//    1 to R.drawable.frame_26,
//    2 to R.drawable.frame_26,
//    3 to R.drawable.frame_21,
//    45 to R.drawable.frame_23,
//    48 to R.drawable.frame_23,
//    51 to R.drawable.frame_19,
//    53 to R.drawable.frame_19,
//    55 to R.drawable.frame_19,
//    56 to R.drawable.frame_22,
//    57 to R.drawable.frame_22,
//    61 to R.drawable.frame_24,
//    63 to R.drawable.frame_24,
//    65 to R.drawable.frame_24,
//    66 to R.drawable.frame_22,
//    67 to R.drawable.frame_22,
//    71 to R.drawable.frame_30,
//    73 to R.drawable.frame_30,
//    75 to R.drawable.frame_30,
//    77 to R.drawable.frame_30,
//    80 to R.drawable.frame_27,
//    81 to R.drawable.frame_27,
//    82 to R.drawable.frame_27,
//    85 to R.drawable.frame_27,
//    86 to R.drawable.frame_27,
//    95 to R.drawable.frame_25,
//    96 to R.drawable.frame_25,
//    99 to R.drawable.frame_25,
//)