package com.count_out.data.repository

import com.count_out.data.entity.SetViewIdD
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
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.TypeRepo.ActivitiesT
import com.count_out.domain.entity.TypeRepo.ActivityT
import com.count_out.domain.entity.TypeRepo.BleConnectStateT
import com.count_out.domain.entity.TypeRepo.BooleanT
import com.count_out.domain.entity.TypeRepo.CollapsingT
import com.count_out.domain.entity.TypeRepo.DeviceUIT
import com.count_out.domain.entity.TypeRepo.DevicesUIT
import com.count_out.domain.entity.TypeRepo.ExerciseT
import com.count_out.domain.entity.TypeRepo.ExercisesT
import com.count_out.domain.entity.TypeRepo.IntT
import com.count_out.domain.entity.TypeRepo.LongT
import com.count_out.domain.entity.TypeRepo.LongsT
import com.count_out.domain.entity.TypeRepo.NullT
import com.count_out.domain.entity.TypeRepo.PlanT
import com.count_out.domain.entity.TypeRepo.PlansT
import com.count_out.domain.entity.TypeRepo.RingT
import com.count_out.domain.entity.TypeRepo.RingsT
import com.count_out.domain.entity.TypeRepo.RoundT
import com.count_out.domain.entity.TypeRepo.RoundsT
import com.count_out.domain.entity.TypeRepo.SetT
import com.count_out.domain.entity.TypeRepo.SetsT
import com.count_out.domain.entity.TypeRepo.SettingT
import com.count_out.domain.entity.TypeRepo.SettingsT
import com.count_out.domain.entity.TypeRepo.ShowBottomSheetT
import com.count_out.domain.entity.TypeRepo.SpeechKitT
import com.count_out.domain.entity.TypeRepo.SpeechT
import com.count_out.domain.entity.TypeRepo.StepPlanT
import com.count_out.domain.entity.TypeRepo.StringT
import com.count_out.domain.entity.TypeRepo.WeatherRequestT
import com.count_out.domain.entity.TypeRepo.WeatherT
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
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
            is TypeSource.DevicesUIT -> DevicesUIT(item = value.item)
            is TypeSource.WeatherT -> WeatherT(item = value.item)
            is TypeSource.WeatherRequestT-> WeatherRequestT(item = value.item)
            is TypeSource.NullT -> NullT
            is TypeSource.BleConnectStateT -> BleConnectStateT(item = value.item)
            is TypeSource.SetViewIdT -> NullT
        }
    }
    fun toTypeSource(value: TypeRepo): TypeSource{
        return when(value){
            is IntT -> TypeSource.IntT(item = value.item)
            is LongT -> TypeSource.LongT(item = value.item)
            is LongsT-> TypeSource.LongsT(item = value.item)
            is StringT -> TypeSource.StringT(item = value.item)
            is BooleanT -> TypeSource.BooleanT(item = value.item)
            is CollapsingT -> TypeSource.CollapsingT(item = value.item)
            is ShowBottomSheetT -> TypeSource.ShowBottomSheetT(item = value.item)
            is SpeechT -> TypeSource.SpeechT(item = SpeechImplD(value.item))
            is SpeechKitT -> TypeSource.SpeechKitT(item = SpeechKitImplD(value.item))
            is SetT -> TypeSource.SetT(item = SetImplD( value.item))
            is SetsT -> TypeSource.SetsT(item = value.item)
            is ActivityT -> TypeSource.ActivityT(item = ActivityImplD(value.item))
            is ActivitiesT -> TypeSource.ActivitiesT(item = value.item)
            is ExerciseT -> TypeSource.ExerciseT(item = ExerciseImplD(value.item))
            is ExercisesT -> TypeSource.ExercisesT(item = value.item)
            is RingT-> TypeSource.RingT(item = RingImplD(value.item))
            is RingsT-> TypeSource.RingsT(item = value.item)
            is RoundT-> TypeSource.RoundT(item = RoundImplD(value.item))
            is RoundsT-> TypeSource.RoundsT(item = value.item)
            is PlanT -> TypeSource.PlanT(item = TrainingImplD(value.item))
            is PlansT -> TypeSource.PlansT(item = value.item)
            is StepPlanT -> TypeSource.StepPlanT(item = value.item)
            is SettingT -> TypeSource.SettingT(item = value.item)
            is SettingsT -> TypeSource.SettingsT(item = value.item)
            is DeviceUIT-> TypeSource.DeviceUIT(item = value.item)
            is DevicesUIT -> TypeSource.DevicesUIT(item = value.item)
            is WeatherT -> TypeSource.WeatherT(item = value.item)
            is WeatherRequestT-> TypeSource.WeatherRequestT(item = value.item)
            is NullT -> TypeSource.NullT
            is BleConnectStateT -> TypeSource.BleConnectStateT(item = value.item)
            is TypeRepo.SetViewIdT -> TypeSource.SetViewIdT(item = SetViewIdD(value.item))
        }
    }
}