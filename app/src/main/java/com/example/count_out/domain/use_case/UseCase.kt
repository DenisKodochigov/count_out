package com.example.count_out.domain.use_case

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
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
    fun execute(input: I): Flow<ResultUC<O>> = executeData(input)
            .map { ResultUC.Success(it) as ResultUC<O> }
            .flowOn(configuration.dispatcher)
            .catch { emit(ResultUC.Error(ThrowableUC.extractThrowable(it)) as ResultUC<Nothing>) }
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