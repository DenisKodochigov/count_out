package com.count_out.device.location.models

sealed class ThrowableLoc (private val t: Throwable?): Throwable(t){
    class RequestLocationFailed(t:Throwable = Exception("request location filed")): ThrowableLoc(t)
    class ReturnNull(t:Throwable = Exception("return null")): ThrowableLoc(t)
    class UnknownThrow(t:Throwable): ThrowableLoc(t)
    companion object {
        fun extract(t: Throwable): ThrowableLoc = if (t is ThrowableLoc) t else UnknownThrow(t)
    }
}