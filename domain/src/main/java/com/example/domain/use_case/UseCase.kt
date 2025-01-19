package com.example.domain.use_case

import com.example.domain.entity.throwable.Result
import com.example.domain.entity.throwable.ThrowableUC
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
/**
 * В этом шаблоне мы определили абстракцию объектов передачи данных, а также класс Configuration,
 * который содержит CoroutineDispatcher. Цель создания этого класса Configuration заключается в том,
 * чтобы иметь возможность добавлять другие параметры для UseCase без изменения подклассов UseCase.
 * У нас есть один абстрактный метод, который будет реализован подклассами для извлечения данных из
 * хранилищ, и метод execute, который возьмет данные и преобразует их в Result, обработает сценарии
 * ошибок и установит соответствующий CoroutineDispatcher.
 * */
abstract class UseCase<I: UseCase.Request, O: UseCase.Response>(
    private val configuration: Configuration
) {
    fun execute(input: I): Flow<Result<O>> = executeData(input)
            .map { Result.Success(it) as Result<O> }
            .flowOn(configuration.dispatcher)
            .catch { emit(Result.Error(ThrowableUC.extractThrowable(it)) as Result<Nothing>) }
    class Configuration(val dispatcher: CoroutineDispatcher)
    internal abstract fun executeData(input: I): Flow<O>
    interface Request
    interface Response
}
//
//
//sealed class SC(){
//    data class DC(val data: String): SC()
//}
//data class DC(val data: String): SC()
//
//fun rrr(){
//    val res = Result.Success("success")
//    var rrrr = res as Result<String>
//    val dc = DC("1")
//    val dc1 = SC.DC("2")
//}
//
//class Box< out T: Animal>(val animal: T)
//open class Animal()
//class Cat : Animal()
//
//fun main() {
//    val a: Animal = Cat()  //так можно
//    val b: Box<Animal> = Box<Cat>(Cat())  //а вот так не получится
//}