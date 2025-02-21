package com.count_out.data.throwable

sealed class ThrowableSD (private val t: Throwable?): Throwable(t){
    class DataStoreThrow(t:Throwable): ThrowableSD(t)
    class RoomThrow(t:Throwable): ThrowableSD(t)
    class WeatherTrow(t:Throwable): ThrowableSD(t)
    class UnknownThrow(t:Throwable): ThrowableSD(t)
    companion object {
        fun extractThrowable(t: Throwable): ThrowableSD = if (t is ThrowableSD) t else UnknownThrow(t)
    }
}