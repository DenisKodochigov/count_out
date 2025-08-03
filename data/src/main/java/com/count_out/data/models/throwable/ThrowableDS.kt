package com.count_out.data.models.throwable

sealed class ThrowableDS (private val t: Throwable?): Throwable(t){
    class TrainingThrow(t:Throwable): ThrowableDS(t)
    class ActivityThrow(t:Throwable): ThrowableDS(t)
    class WeatherTrow(t:Throwable): ThrowableDS(t)
    class RequestFailed(t:Throwable = Exception("return null")): ThrowableDS(t)
    class ReturnNull(t:Throwable = Exception("return null")): ThrowableDS(t)
    class NotValidType(t:Throwable = Exception("not valid type")): ThrowableDS(t)
    class SQlError(t:Throwable): ThrowableDS(t)
    class UnknownThrow(t:Throwable): ThrowableDS(t)
    companion object {
        fun extract(t: Throwable): ThrowableDS = if (t is ThrowableDS) t else UnknownThrow(t)
    }
}