package com.count_out.entity.enums

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
}