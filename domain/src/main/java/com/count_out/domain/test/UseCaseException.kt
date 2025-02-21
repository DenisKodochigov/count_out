package com.count_out.domain.test

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

sealed class UseCaseException111(private val throwable: Throwable): Throwable(throwable) {
    class Entity1Exception(throwable: Throwable): UseCaseException111(throwable)
    class Error
}

sealed class Throw(private val t: Throwable?): Throwable(t){
    class E1U(t:Throwable): Throw(t)
    class E2U(t:Throwable): Throw(t)
    class E3U(t:Throwable): Throw(t)
    class Unk(t:Throwable): Throw(t)
    companion object {
        fun extractThrowable(t: Throwable): Throw = if (t is Throw) t else Unk(t)
    }
}
sealed class Res <out T: Any>{
    class Success <out T : Any>(val data: T): Res<T>()
    class Error(val error: Throw): Res<Nothing>()
}
abstract class UC<T: UC.Request, R: UC.Response> (private val per: CoroutineDispatcher){
    fun execute( input: T): Flow<Res<R>> =
        executeData(input)
            .map { Res.Success(it) as Res<R> }
            .flowOn(per)
            .catch { emit(Res.Error(Throw.extractThrowable(it))) }

    internal abstract fun executeData(input: T): Flow<R>
    interface Request
    interface Response
}

fun test(){
//    val u = Uuu.E1U()
//    u.print()
    val r = Res.Success("data")
    val r1 = Res.Success(1)
//    val e = Res.Error(u)
//    println("$e")
}