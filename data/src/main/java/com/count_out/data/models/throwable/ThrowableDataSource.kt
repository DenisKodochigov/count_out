package com.count_out.data.models.throwable

sealed class ThrowableDataSource (private val t: Throwable?): Throwable(t){
    class TrainingThrow(t:Throwable): ThrowableDataSource(t)
    class ActivityThrow(t:Throwable): ThrowableDataSource(t)
    class WeatherTrow(t:Throwable): ThrowableDataSource(t)
    class UnknownThrow(t:Throwable): ThrowableDataSource(t)
    companion object {
        fun extractThrowable(t: Throwable): ThrowableDataSource = if (t is ThrowableDataSource) t else UnknownThrow(t)
    }
}