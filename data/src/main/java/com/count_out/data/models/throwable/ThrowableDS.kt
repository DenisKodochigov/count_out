package com.count_out.data.models.throwable

sealed class ThrowableDS (private val t: Throwable?): Throwable(t){
    class TrainingThrow(t:Throwable): ThrowableDS(t)
    class ActivityThrow(t:Throwable): ThrowableDS(t)
    class WeatherTrow(t:Throwable): ThrowableDS(t)
    class RequestFailed(t:Throwable = Exception("return null")): ThrowableDS(t)
    class RequestNotCondition(t:Throwable = Exception("ResultData.flatMapCondition return null")): ThrowableDS(t)
    class ReturnNull(t:Throwable = Exception("return null")): ThrowableDS(t)
    class NotValidType(t:Throwable = Exception("not valid type")): ThrowableDS(t)
    class ErrorUpdate(t:Throwable = Exception("Update return 0")): ThrowableDS(t)
    class ErrorBleIsEmpty(t:Throwable = Exception("BleDataMapDb this.item.isEmpty")): ThrowableDS(t)
    class ErrorBoolean(t:Throwable = Exception("Boolean false")): ThrowableDS(t)
    class ErrorString(t:Throwable = Exception("String empty")): ThrowableDS(t)
    class ErrorLong(t:Throwable = Exception("Long = 0")): ThrowableDS(t)
    class ErrorPlans(t:Throwable = Exception("List plans empty")): ThrowableDS(t)
    class ErrorSpeeches(t:Throwable = Exception("List speeches empty")): ThrowableDS(t)
    class ErrorExercises(t:Throwable = Exception("List exercises empty")): ThrowableDS(t)
    class ErrorActivities(t:Throwable = Exception("List activity empty")): ThrowableDS(t)
    class ErrorSQl(t:Throwable): ThrowableDS(t)
    class UnknownThrow(t:Throwable): ThrowableDS(t)
    companion object {
        fun extract(t: Throwable): ThrowableDS = if (t is ThrowableDS) t else UnknownThrow(t)
    }
}