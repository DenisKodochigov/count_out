package com.count_out.domain.use_case

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * В этом шаблоне мы определили абстракцию объектов передачи данных, а также класс Configuration,
 * который содержит CoroutineDispatcher. Цель создания этого класса Configuration заключается в том,
 * чтобы иметь возможность добавлять другие параметры для UseCase без изменения подклассов UseCase.
 * У нас есть один абстрактный метод, который будет реализован подклассами для извлечения данных из
 * хранилищ, и метод execute, который возьмет данные и преобразует их в Result, обработает сценарии
 * ошибок и установит соответствующий CoroutineDispatcher.
 * */
abstract class UseCase< I: UseCase.Request, O: UseCase.Response>(private val configuration: Configuration,
) {
    class Configuration(val dispatcher: CoroutineDispatcher)
    interface Request
    interface Response
    internal abstract fun method(request: I): Flow<ResultDomain<Domain>>
    internal abstract fun response(result:Domain): O
    fun execute(request: I): Flow<ResultDomain<O>> = method(request).map{ result->
        when(result){
            is ResultDomain.Success-> { ResultDomain.Success(response(result.data)) }
            is ResultDomain.Error -> result as ResultDomain<Nothing>
        }
    }

    fun Flow<ResultDomain<Domain>>.wrap(convertTR: (Domain)-> Domain): Flow<ResultDomain<Domain>>{
        return this.map {resultUcTypeRepo->
            if ( resultUcTypeRepo is ResultDomain.Success) {
                convertTR(resultUcTypeRepo.data).let { st-> ResultDomain.Success(st) }
            } else resultUcTypeRepo
        }
    }
    val exceptionNull = ResultDomain.Error(
        ThrowableUC.extract(Exception("return null"))) as ResultDomain<Nothing>
//###############################################################################################

}

//    fun exec_old(request: I): Flow<ResultUC<O>> = implementation_old(request)
//        .map { ResultUC.Success(it) as ResultUC<O> }
//        .flowOn(configuration.dispatcher)
//        .catch { emit(ResultUC.Error(ThrowableUC.extractThrowable(it)) as ResultUC<Nothing>) }
//
//    internal abstract fun implementation_old(request: I): Flow<O>
//    fun <T : Any>converter_old(result: ResultUC<T>, response:(T?)->O): Response{
//        return when(result){
//            is ResultUC.Success-> response(result.data)
//            is ResultUC.Error -> response(null)
//        }
//    }


//    fun execute(request: I): Flow<ResultUC<O>> = implementation(request)
//    internal abstract fun implementation(request: I): Flow<ResultUC<O>>
//
//    fun converterR(result: ResultUC<TypeRepo>, response:(TypeRepo)->O): ResultUC<O>{
//        return when(result){
//            is ResultUC.Success-> ResultUC.Success(response(result.data))
//            is ResultUC.Error -> result as ResultUC<Nothing>
//        }
//    }
//
//    fun <T: Any, U: Any>convertor3( request: Flow<ResultUC<T>>, convertTR: (T?)-> U?): Flow<ResultUC<U>>{
//        return request.map { result->
//            if ( result is ResultUC.Success) {
//                convertTR(result.data)?.let { st-> ResultUC.Success(st) } ?: exceptionNull
//            } else exceptionNull
//        }
//    }
//    fun convertor4( request: Flow<ResultUC<TypeRepo>>, convertTR: (TypeRepo?)-> TypeRepo?): Flow<ResultUC<TypeRepo>>{
//        return request.map { result->
//            if ( result is ResultUC.Success) {
//                convertTR(result.data)?.let { st-> ResultUC.Success(st) } ?: exceptionNull
//            } else exceptionNull
//        }
//    }