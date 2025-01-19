package com.example.domain.entity.throwable

sealed class ThrowableUC (private val t: Throwable?): Throwable(t){
    class TrainingThrow(t:Throwable): ThrowableUC(t)
    class ActivityThrow(t:Throwable): ThrowableUC(t)
    class WeatherTrow(t:Throwable): ThrowableUC(t)
    class UnknownThrow(t:Throwable): ThrowableUC(t)
    companion object {
        fun extractThrowable(t: Throwable): ThrowableUC = if (t is ThrowableUC) t else UnknownThrow(t)
    }
}