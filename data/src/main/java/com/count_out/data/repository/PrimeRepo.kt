package com.count_out.data.repository

import com.count_out.data.models.ActivityImplD
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.RingImplD
import com.count_out.data.models.RoundImplD
import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.TypeSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.TypeRepo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
abstract class PrimeRepo {
    val throwableNull = ResultUC.Error(ThrowableUC.extract(Exception("return null")))

    @OptIn(ExperimentalCoroutinesApi::class)
    fun Flow<ResultSource<TypeSource>>.concat1(action: (TypeSource)-> Flow<ResultSource<TypeSource>>
    ): Flow<ResultUC<TypeRepo>> {
        return this.flatMapConcat { resultDS ->
            when (resultDS) {
                is ResultSource.Error -> flow { emit(
                    ResultUC.Error(ThrowableUC.extract(resultDS.throwable))) }
                is Success -> {
                    action(resultDS.data).map {resultSource ->
                        when(resultSource){
                            is Success-> { ResultUC.Success( toTypeRepo(resultSource.data))}
                            is ResultSource.Error -> { ResultUC.Error(ThrowableUC.extract(resultSource.throwable))}
                        }
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun Flow<ResultSource<TypeSource>>.concatOk(action: ()-> Flow<ResultSource<TypeSource>>
    ): Flow<ResultUC<TypeRepo>> {
        return this.flatMapConcat {
            when (it) {
                is ResultSource.Error -> flow { emit(
                    ResultUC.Error(ThrowableUC.extract(it.throwable))) }
                is Success -> {
                    if(getResult(it.data) > 0L ){
                        action().map {resultSource ->
                            when(resultSource){
                                is Success-> { ResultUC.Success( toTypeRepo(resultSource.data))}
                                is ResultSource.Error -> { ResultUC.Error(ThrowableUC.extract(resultSource.throwable))}
                            }
                        }
                    } else { flow { emit(throwableNull) } }
                }
            }
        }
    }
    fun getResult(resultSource: TypeSource): Int{
        return when(resultSource){
            is TypeSource.IntT -> resultSource.item
            is TypeSource.LongT -> resultSource.item.toInt()
            is TypeSource.BooleanT -> if (resultSource.item) 1 else 0
            else -> 0
        }
    }

    fun Flow<ResultSource<TypeSource>?>.convertor(): Flow<ResultUC<TypeRepo>> {
        var lastValue:TypeSource = TypeSource.NullT
        return this.filter { it != lastValue }.map{ resultSource->
                resultSource?.let { resultS->
                    when(resultS){
                        is ResultSource.Error -> throwableNull
                        is Success -> {
                            lastValue = resultS.data
                            ResultUC.Success(toTypeRepo(resultS.data))
                        }
                    }
                } ?: throwableNull
            }
            .flowOn(Dispatchers.IO)
            .catch { emit(ResultUC.Error(ThrowableUC.extract(it)
            ) as ResultUC<Nothing>) }
    }
    fun convertor(source: ResultSource<TypeSource>): ResultUC<TypeRepo> {
        return when (source) {
            is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(source.throwable))
            is Success -> ResultUC.Success(toTypeRepo(source.data))
        }
    }
    fun wrapFlow(source: ResultSource<TypeSource>): Flow<ResultUC<TypeRepo>> {
        return flow { emit(
            when (source) {
                is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(source.throwable))
                is Success -> ResultUC.Success(toTypeRepo(source.data))
            }
        ) } }
    fun toTypeRepo(value: TypeSource): TypeRepo {
        return when(value){
            is TypeSource.IntT -> IntT(item = value.item)
            is TypeSource.LongT -> LongT(item = value.item)
            is TypeSource.LongsT-> LongsT(item = value.item)
            is TypeSource.StringT -> StringT(item = value.item)
            is TypeSource.BooleanT -> BooleanT(item = value.item)
            is TypeSource.CollapsingT -> CollapsingT(item = value.item)
            is TypeSource.ShowBottomSheetT -> ShowBottomSheetT(item = value.item)
            is TypeSource.SpeechT -> SpeechT(item = value.item)
            is TypeSource.SpeechKitT -> SpeechKitT(item = value.item)
            is TypeSource.SetT -> SetT(item = value.item)
            is TypeSource.SetsT -> SetsT(item = value.item)
            is TypeSource.ActivityT -> ActivityT(item = value.item)
            is TypeSource.ActivitiesT -> ActivitiesT(item = value.item)
            is TypeSource.ExerciseT -> ExerciseT(item = value.item)
            is TypeSource.ExercisesT -> ExercisesT(item = value.item)
            is TypeSource.RingT-> RingT(item = value.item)
            is TypeSource.RingsT-> RingsT(item = value.item)
            is TypeSource.RoundT-> RoundT(item = value.item)
            is TypeSource.RoundsT-> RoundsT(item = value.item)
            is TypeSource.PlanT -> PlanT(item = value.item)
            is TypeSource.PlansT -> PlansT(item = value.item)
            is TypeSource.StepPlanT -> StepPlanT(item = value.item)
            is TypeSource.SettingT -> SettingT(item = value.item)
            is TypeSource.SettingsT -> SettingsT(item = value.item)
            is TypeSource.DeviceUIT -> DeviceUIT(item = value.item)
            is TypeSource.DevicesUIT -> TypeRepo.DevicesUIT(item = value.item)
            is TypeSource.WeatherT -> WeatherT(item = value.item)
            is TypeSource.WeatherRequestT-> WeatherRequestT(item = value.item)
            is TypeSource.DataForChangeSequenceT-> DataForChangeSequenceT(item = value.item)
            is TypeSource.NullT -> TypeRepo.NullT
        }
    }
    fun toTypeSource(value: TypeRepo): TypeSource{
        return when(value){
            is TypeRepo.IntT -> TypeSource.IntT(item = value.item)
            is TypeRepo.LongT -> TypeSource.LongT(item = value.item)
            is TypeRepo.LongsT-> TypeSource.LongsT(item = value.item)
            is TypeRepo.StringT -> TypeSource.StringT(item = value.item)
            is TypeRepo.BooleanT -> TypeSource.BooleanT(item = value.item)
            is TypeRepo.CollapsingT -> TypeSource.CollapsingT(item = value.item)
            is TypeRepo.ShowBottomSheetT -> TypeSource.ShowBottomSheetT(item = value.item)
            is TypeRepo.SpeechT -> TypeSource.SpeechT(item = SpeechImplD(value.item))
            is TypeRepo.SpeechKitT -> TypeSource.SpeechKitT(item = SpeechKitImplD(value.item))
            is TypeRepo.SetT -> TypeSource.SetT(item = SetImplD( value.item))
            is TypeRepo.SetsT -> TypeSource.SetsT(item = value.item)
            is TypeRepo.ActivityT -> TypeSource.ActivityT(item = ActivityImplD(value.item))
            is TypeRepo.ActivitiesT -> TypeSource.ActivitiesT(item = value.item)
            is TypeRepo.ExerciseT -> TypeSource.ExerciseT(item = ExerciseImplD(value.item))
            is TypeRepo.ExercisesT -> TypeSource.ExercisesT(item = value.item)
            is TypeRepo.RingT-> TypeSource.RingT(item = RingImplD(value.item))
            is TypeRepo.RingsT-> TypeSource.RingsT(item = value.item)
            is TypeRepo.RoundT-> TypeSource.RoundT(item = RoundImplD(value.item))
            is TypeRepo.RoundsT-> TypeSource.RoundsT(item = value.item)
            is TypeRepo.PlanT -> TypeSource.PlanT(item = TrainingImplD(value.item))
            is TypeRepo.PlansT -> TypeSource.PlansT(item = value.item)
            is TypeRepo.StepPlanT -> TypeSource.StepPlanT(item = value.item)
            is TypeRepo.SettingT -> TypeSource.SettingT(item = value.item)
            is TypeRepo.SettingsT -> TypeSource.SettingsT(item = value.item)
            is TypeRepo.DeviceUIT-> TypeSource.DeviceUIT(item = value.item)
            is TypeRepo.DevicesUIT -> TypeSource.DevicesUIT(item = value.item)
            is TypeRepo.WeatherT -> TypeSource.WeatherT(item = value.item)
            is TypeRepo.WeatherRequestT-> TypeSource.WeatherRequestT(item = value.item)
            is TypeRepo.DataForChangeSequenceT-> TypeSource.DataForChangeSequenceT(item = value.item)
            is TypeRepo.NullT -> TypeSource.NullT
        }
    }
}