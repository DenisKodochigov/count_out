package com.count_out.domain.entity.throwable

sealed class ThrowableUC (private val t: Throwable?): Throwable(t){
    class TrainingThrow(t:Throwable): ThrowableUC(t)
    class ActivityThrow(t:Throwable): ThrowableUC(t)
    class WeatherTrow(t:Throwable): ThrowableUC(t)
    class RepoTrow(t:Throwable): ThrowableUC(t)
    class DataSourceTrow(t:Throwable): ThrowableUC(t)
    class RequestFailed(t:Throwable = Exception("return null")): ThrowableUC(t)
    class ReturnNull(t:Throwable = Exception("return null")): ThrowableUC(t)
    class NotValidType(t:Throwable = Exception("not valid type")): ThrowableUC(t)
    class UnknownThrow(t:Throwable): ThrowableUC(t)
    companion object {
        fun extract(t: Throwable): ThrowableUC = if (t is ThrowableUC) t else UnknownThrow(t)
    }
}