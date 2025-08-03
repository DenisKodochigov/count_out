package com.count_out.data.entity

import com.count_out.data.models.throwable.ResultSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC

class ConverterResult {
//    fun <T: Any>execute(resultDS: ResultSource<T> ): ResultUC<T> {
//        return when(resultDS){
//            is ResultSource.Success-> { ResultUC.Success( resultDS.data)}
//            is ResultSource.Error -> { ResultUC.Error(ThrowableUC.DataSourceTrow(resultDS.throwable))}
//        }
//    }

//    fun execute1(resultDS: ResultSource<TypeSource> ): ResultUC<TypeRepo> {
//        return when(resultDS){
//            is ResultSource.Success-> { ResultUC.Success( convertorType(resultDS.data))}
//            is ResultSource.Error -> { ResultUC.Error(ThrowableUC.extract(resultDS.throwable))}
//        } as ResultUC<TypeRepo>
//    }

//    fun convertorType(value: TypeSource): TypeRepo{
//        return when(value){
//            is TypeSource.IntT -> IntT(item = value.item)
//            is TypeSource.LongT -> LongT(item = value.item)
//            is TypeSource.NullT -> NullT
//            is TypeSource.PlanT -> PlanT(item = value.item)
//            is TypeSource.SpeechT -> SpeechT(item = value.item)
//            is TypeSource.SpeechKitT -> SpeechKitT(item = value.item)
//            is TypeSource.StringT -> StringT(item = value.item)
//            is TypeSource.Activities -> Activities(item = value.item)
//            is TypeSource.ActivityT -> ActivityT(item = value.item)
//            is TypeSource.BooleanT -> BooleanT(item = value.item)
//            is TypeSource.CollapsingT -> CollapsingT(item = value.item)
//            is TypeSource.ExerciseT -> ExerciseT(item = value.item)
//            is TypeSource.ListExercise -> ListExercise(item = value.item)
//            is TypeSource.ListPlan -> ListPlan(item = value.item)
//            is TypeSource.SetT -> SetT(item = value.item)
//            is TypeSource.Sets -> Sets(item = value.item)
//            is TypeSource.SettingsT -> SettingsT(item = value.item)
//            is TypeSource.ShowBottomSheetT -> ShowBottomSheetT(item = value.item)
//            is TypeSource.StepPlanT -> StepPlanT(item = value.item)
//            is TypeSource.WeatherT -> WeatherT(item = value.item)
//        }
//    }
}