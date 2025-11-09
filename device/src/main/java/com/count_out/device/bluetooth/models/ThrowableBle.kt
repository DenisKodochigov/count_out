package com.count_out.device.bluetooth.models

sealed class ThrowableBle (private val t: Throwable?): Throwable(t){
    class RequestFailed(t:Throwable = Exception("return null")): ThrowableBle(t)
    class ReturnNull(t:Throwable = Exception("return null")): ThrowableBle(t)
    class NoDevice(t:Throwable = Exception("no bluetooth device")): ThrowableBle(t)
    class NotConnectingGatt(t:Throwable = Exception("no connecting gatt")): ThrowableBle(t)
    class ErrorAddress(t:Throwable = Exception("Address device not string")): ThrowableBle(t)
    class ErrorDevice(t:Throwable = Exception("Result device not device")): ThrowableBle(t)
    class NotValidType(t:Throwable = Exception("not valid type")): ThrowableBle(t)
    class NotValidBle(t:Throwable = Exception("not valid bluetooth")): ThrowableBle(t)
    class Scanning(t:Throwable = Exception("error start scanning")): ThrowableBle(t)
    class ClearCache(t:Throwable = Exception("error clear cache")): ThrowableBle(t)
    class UnknownThrow(t:Throwable): ThrowableBle(t)
    companion object {
        fun extract(t: Throwable): ThrowableBle = if (t is ThrowableBle) t else UnknownThrow(t)
    }
}