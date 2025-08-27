package com.count_out.data.source

import android.R.attr.data
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class PrimeSource {

    fun throwableException(e: ThrowableDS): ResultSource<TypeSource> =
        ResultSource.Error(ThrowableDS.extract(e)) as ResultSource<Nothing>

//###################################################################################
    fun Flow<TypeSource?>.resultSource(): Flow<ResultSource<TypeSource>> {
        var lastValue:TypeSource? = null
        return this.filterNotNull().filter { it != lastValue }
            .map { lastValue = it
                    Success(it) as ResultSource<TypeSource> }
            .flowOn(Dispatchers.IO)
            .catch { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
    }

//
//    fun wrapResult(content:()->TypeSource?): ResultSource<TypeSource> {
//        return try {
//            content()?.let { Success(it) as ResultSource<TypeSource> } ?: ResultSource.Error(ThrowableDS.ReturnNull()) }
//        catch (e: Exception) {ResultSource.Error(ThrowableDS.extract(e)) as ResultSource<Nothing>}
//    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    fun Flow<ResultSource<TypeSource>>.concat(
//        action: (TypeSource)-> Flow<ResultSource<TypeSource>>
//    ): Flow<ResultSource<TypeSource>> {
//        return this.flatMapConcat { resultDS ->
//            when (resultDS) {
//                is ResultSource.Error -> flow { emit(
//                    ResultSource.Error(ThrowableDS.extract(resultDS.throwable))) }
//                is Success -> { action(resultDS.data)}
//            }
//        }
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    fun Flow<ResultSource<TypeSource>>.concatOk(
//        action: ()-> Flow<ResultSource<TypeSource>>
//    ): Flow<ResultSource<TypeSource>> {
//        return this.flatMapConcat { resultDS ->
//            when (resultDS) {
//                is ResultSource.Error -> flow { emit(throwableException(resultDS.throwable)) }
//                is Success -> {
//                    if ( hecNumber(resultDS.data) > 0L ){  action()
//                    } else { flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) } }
//                }
//            }
//        }
//    }
//
//    fun hecNumber(resultSource: TypeSource): Int{
//        return when(resultSource){
//            is TypeSource.IntT -> resultSource.item
//            is TypeSource.LongT -> resultSource.item.toInt()
//            is TypeSource.BooleanT -> if (resultSource.item) 1 else 0
//            else -> 0
//        }
//    }
//    fun convertorType(value: TypeSource): TypeRepo {
//        return when(value){
//            is TypeSource.IntT -> TypeRepo.IntT(item = value.item)
//            is TypeSource.LongT -> TypeRepo.LongT(item = value.item)
//            is TypeSource.NullT -> TypeRepo.NullT
//            is TypeSource.PlanT -> TypeRepo.PlanT(item = value.item)
//            is TypeSource.SpeechT -> TypeRepo.SpeechT(item = value.item)
//            is TypeSource.SpeechKitT -> TypeRepo.SpeechKitT(item = value.item)
//            is TypeSource.StringT -> TypeRepo.StringT(item = value.item)
//            is TypeSource.Activities -> TypeRepo.Activities(item = value.item)
//            is TypeSource.ActivityT -> TypeRepo.ActivityT(item = value.item)
//            is TypeSource.BooleanT -> TypeRepo.BooleanT(item = value.item)
//            is TypeSource.CollapsingT -> TypeRepo.CollapsingT(item = value.item)
//            is TypeSource.ExerciseT -> TypeRepo.ExerciseT(item = value.item)
//            is TypeSource.ListExercise -> TypeRepo.ListExercise(item = value.item)
//            is TypeSource.ListPlan -> TypeRepo.ListPlan(item = value.item)
//            is TypeSource.SetT -> TypeRepo.SetT(item = value.item)
//            is TypeSource.Sets -> TypeRepo.Sets(item = value.item)
//            is TypeSource.SettingT -> TypeRepo.SettingT(item = value.item)
//            is TypeSource.SettingsT -> TypeRepo.SettingsT(item = value.item)
//            is TypeSource.ShowBottomSheetT -> TypeRepo.ShowBottomSheetT(item = value.item)
//            is TypeSource.StepPlanT -> TypeRepo.StepPlanT(item = value.item)
//            is TypeSource.WeatherT -> TypeRepo.WeatherT(item = value.item)
//            is TypeSource.DeviceUIT -> TypeRepo.DeviceUIT(item = value.item)
//            is TypeSource.DataForChangeSequenceT-> TypeRepo.DataForChangeSequenceT(item = value.item)
//            is TypeSource.LongsT-> TypeRepo.LongsT(item = value.item)
//            is TypeSource.RingT-> TypeRepo.RingT(item = value.item)
//            is TypeSource.RoundT-> TypeRepo.RoundT(item = value.item)
//            is TypeSource.RingsT-> TypeRepo.RingsT(item = value.item)
//            is TypeSource.RoundsT-> TypeRepo.RoundsT(item = value.item)
//            is TypeSource.WeatherRequestT-> TypeRepo.WeatherRequestT(item = value.item)
//        }
//    }
//    fun convertorType(value: TypeRepo): TypeSource{
//        return when(value){
//            is TypeRepo.IntT -> TypeSource.IntT(item = value.item)
//            is TypeRepo.LongT -> TypeSource.LongT(item = value.item)
//            is TypeRepo.NullT -> TypeSource.NullT
//            is TypeRepo.PlanT -> TypeSource.PlanT(item = value.item)
//            is TypeRepo.SpeechT -> TypeSource.SpeechT(item = value.item)
//            is TypeRepo.SpeechKitT -> TypeSource.SpeechKitT(item = value.item)
//            is TypeRepo.StringT -> TypeSource.StringT(item = value.item)
//            is TypeRepo.Activities -> TypeSource.Activities(item = value.item)
//            is TypeRepo.ActivityT -> TypeSource.ActivityT(item = value.item)
//            is TypeRepo.BooleanT -> TypeSource.BooleanT(item = value.item)
//            is TypeRepo.CollapsingT -> TypeSource.CollapsingT(item = value.item)
//            is TypeRepo.ExerciseT -> TypeSource.ExerciseT(item = value.item)
//            is TypeRepo.ListExercise -> TypeSource.ListExercise(item = value.item)
//            is TypeRepo.ListPlan -> TypeSource.ListPlan(item = value.item)
//            is TypeRepo.SetT -> TypeSource.SetT(item = value.item)
//            is TypeRepo.Sets -> TypeSource.Sets(item = value.item)
//            is TypeRepo.SettingT -> TypeSource.SettingT(item = value.item)
//            is TypeRepo.SettingsT -> TypeSource.SettingsT(item = value.item)
//            is TypeRepo.ShowBottomSheetT -> TypeSource.ShowBottomSheetT(item = value.item)
//            is TypeRepo.StepPlanT -> TypeSource.StepPlanT(item = value.item)
//            is TypeRepo.WeatherT -> TypeSource.WeatherT(item = value.item)
//            is TypeRepo.DeviceUIT-> TypeSource.DeviceUIT(item = value.item)
//            is TypeRepo.DataForChangeSequenceT-> TypeSource.DataForChangeSequenceT(item = value.item)
//            is TypeRepo.LongsT-> TypeSource.LongsT(item = value.item)
//            is TypeRepo.RingT-> TypeSource.RingT(item = value.item)
//            is TypeRepo.RoundT-> TypeSource.RoundT(item = value.item)
//            is TypeRepo.RingsT-> TypeSource.RingsT(item = value.item)
//            is TypeRepo.RoundsT-> TypeSource.RoundsT(item = value.item)
//            is TypeRepo.WeatherRequestT-> TypeSource.WeatherRequestT(item = value.item)
//        }
//    }

}

//try {
//    val result = executeData()
//    if (result != null) {
//        ResultDataSource.Success(result) as ResultDataSource<O>
//    } else {
//        ResultDataSource.Error(ThrowableDataSource.extractThrowable(Exception("return null")))
//    }
//} catch (e: Exception) {
//    ResultDataSource.Error(ThrowableDataSource.extractThrowable(e))
//}

//override fun getLastUsedPlan(): Flow<ResultUC<Training>> {
//    return source.getLastPlan().map { resultDataSource->
//        converter.execute(when(resultDataSource){
//            is ResultDataSource.Success-> sourceTraining.get2( resultDataSource.data)
//            is ResultDataSource.Error -> resultDataSource
//        })
//    }
//}

//    fun Flow<TypeSource?>.resultSource(): Flow<ResultSource<TypeSource>> {
//        var lastValue:TypeSource? = null
//        return this.filter { it != lastValue }
//            .map {
//                it?.let {
//                    lastValue = it
//                    Success(it) as ResultSource<TypeSource> }
//                    ?: ResultSource.Error(
//                        ThrowableDS.extract(Exception("return null"))
//                    ) as ResultSource<Nothing>
//            }
//            .flowOn(Dispatchers.IO)
//            .catch { emit(ResultSource.Error(ThrowableDS.extract(it)
//            ) as ResultSource<Nothing>) }
//    }