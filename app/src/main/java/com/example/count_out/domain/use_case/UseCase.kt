package com.example.count_out.domain.use_case

import com.example.count_out.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.example.count_out.R
import com.example.count_out.ui.screens.prime.PrimeConv
import com.example.count_out.ui.screens.prime.PrimeConvertor
import com.example.count_out.ui.screens.prime.ScreenState
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
abstract class UseCase< I: UseCase.Request, O: UseCase.Response>(
    private val configuration: Configuration,
) {
//    fun eeee(input: I): Flow<ScreenState<R>> = execute(input).map { convertor.convert(it) }
    fun execute(input: I): Flow<ResultUC<O>> = executeData(input)
        .map { ResultUC.Success(it) as ResultUC<O> }
        .flowOn(configuration.dispatcher)
        .catch { emit(ResultUC.Error(ThrowableUC.extractThrowable(it)) as ResultUC<Nothing>) }

    class Configuration(val dispatcher: CoroutineDispatcher)
    internal abstract fun executeData(input: I): Flow<O>
    interface Request
    interface Response
}

abstract class UseCase1<R: Any, I: UseCase.Request, O: UseCase.Response>(
    private val configuration: Configuration,
) {
    abstract fun convertor(): PrimeConv<O, R>
    //    fun eeee(input: I): Flow<ScreenState<R>> = execute(input).map { convertor.convert(it) }
    fun execute(input: I): Flow<ResultUC<O>> = executeData(input)
        .map { ResultUC.Success(it) as ResultUC<O> }
        .flowOn(configuration.dispatcher)
        .catch { emit(ResultUC.Error(ThrowableUC.extractThrowable(it)) as ResultUC<Nothing>) }

    fun execute1(input: I): Flow<ScreenState<R>> = executeData(input)
        .map { ResultUC.Success(it) as ResultUC<O> }
        .flowOn(configuration.dispatcher)
        .catch { emit(ResultUC.Error(ThrowableUC.extractThrowable(it)) as ResultUC<Nothing>) }
        .map { convertor().convert(it) }


    class Configuration(val dispatcher: CoroutineDispatcher)
    internal abstract fun executeData(input: I): Flow<O>
    interface Request
    interface Response
}