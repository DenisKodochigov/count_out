package com.count_out.data.models.throwable

import com.count_out.data.models.ActivityImplD
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.RingImpl
import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.TypeRepo

sealed class TypeSource {
    data class IntT(val item: Int): TypeSource()
    data class LongT(val item: Long): TypeSource()
    data class LongsT(val item: List<Long>): TypeSource()
    data class StringT(val item: String): TypeSource()
    data class BooleanT(val item: Boolean): TypeSource()
    data class WeatherT(val item: Weather): TypeSource()
    data class CollapsingT(val item: Collapsing): TypeSource()
    data class ShowBottomSheetT(val item: ShowBottomSheet): TypeSource()
    data class SetT(val item: SetImplD): TypeSource()
    data class SetsT(val item: List<Set>): TypeSource()
    data class RingT(val item: RingImpl): TypeSource()
    data class RingsT(val item: List<Ring>): TypeSource()
    data class RoundT(val item: Round): TypeSource()
    data class RoundsT(val item: List<Round>): TypeSource()
    data class ExerciseT(val item: ExerciseImplD): TypeSource()
    data class ExercisesT(val item: List<Exercise>): TypeSource()
    data class ActivityT(val item: ActivityImplD): TypeSource()
    data class ActivitiesT(val item: List<Activity>): TypeSource()
    data class PlanT(val item: TrainingImplD): TypeSource()
    data class PlansT(val item: List<Training>): TypeSource()
    data class StepPlanT(val item: StepPlan): TypeSource()
    data class SpeechT(val item: SpeechImplD): TypeSource()
    data class SpeechKitT(val item: SpeechKitImplD): TypeSource()
    data class SettingT(val item: Setting): TypeSource()
    data class SettingsT(val item: Settings): TypeSource()
    data class DeviceUIT(val item: DeviceUI): TypeSource()
    data class WeatherRequestT(val item: WeatherRequest): TypeSource()
    data class DataForChangeSequenceT(val item: DataForChangeSequence): TypeSource()
    data object NullT: TypeSource()

    fun toRepo(): TypeRepo = when(this){
        is ActivitiesT -> TypeRepo.ActivitiesT(item = this.item)
        is ActivityT -> TypeRepo.ActivityT(item = this.item)
        is BooleanT -> TypeRepo.BooleanT(item = this.item)
        is CollapsingT -> TypeRepo.CollapsingT(item = this.item)
        is DataForChangeSequenceT -> TypeRepo.DataForChangeSequenceT(item = this.item)
        is DeviceUIT -> TypeRepo.DeviceUIT(item = this.item)
        is ExerciseT -> TypeRepo.ExerciseT(item = this.item)
        is ExercisesT -> TypeRepo.ExercisesT(item = this.item)
        is IntT -> TypeRepo.IntT(item = this.item)
        is LongT -> TypeRepo.LongT(item = this.item)
        is LongsT ->  TypeRepo.LongsT(item = this.item)
        NullT -> TypeRepo.NullT
        is PlanT -> TypeRepo.PlanT(item = this.item)
        is PlansT -> TypeRepo.PlansT(item = this.item)
        is RingT -> TypeRepo.RingT(item = this.item)
        is RingsT -> TypeRepo.RingsT(item = this.item)
        is RoundT -> TypeRepo.RoundT(item = this.item)
        is RoundsT ->  TypeRepo.RoundsT(item = this.item)
        is SetT -> TypeRepo.SetT(item = this.item)
        is SetsT -> TypeRepo.SetsT(item = this.item)
        is SettingT -> TypeRepo.SettingT(item = this.item)
        is SettingsT -> TypeRepo.SettingsT(item = this.item)
        is ShowBottomSheetT -> TypeRepo.ShowBottomSheetT(item = this.item)
        is SpeechKitT -> TypeRepo.SpeechKitT(item = this.item)
        is SpeechT -> TypeRepo.SpeechT(item = this.item)
        is StepPlanT -> TypeRepo.StepPlanT(item = this.item)
        is StringT -> TypeRepo.StringT(item = this.item)
        is WeatherRequestT -> TypeRepo.WeatherRequestT(item = this.item)
        is WeatherT -> TypeRepo.WeatherT(item = this.item)
    }
//    fun <T: TypeSource>result(type1: KClass<T>, content:()-> ResultSource<TypeSource> ): Boolean{
//        return if (type1 == this.javaClass) {
//            if (this is TypeSource) { this}
//            true
//        }
//        else {
//            content()
//            false
//        }
//    }
//    fun <T: TypeSource>checkType(type: KClass<T>): Boolean{
//        return type == this.javaClass
//    }
}
