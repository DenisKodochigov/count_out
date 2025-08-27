package com.count_out.data.models.throwable

import com.count_out.data.entity.SetViewIdD
import com.count_out.data.models.ActivityImplD
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.RingImplD
import com.count_out.data.models.RoundImplD
import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.StepPlan
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceBle
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

sealed class TypeSource {
    data class IntT(val item: Int): TypeSource()
    data class LongT(val item: Long): TypeSource()
    data class LongsT(val item: List<Long>): TypeSource()
    data class StringT(val item: String): TypeSource()
    data class BooleanT(val item: Boolean): TypeSource()
    data class CollapsingT(val item: Collapsing): TypeSource()
    data class ShowBottomSheetT(val item: ShowBottomSheet): TypeSource()
    data class SpeechT(val item: SpeechImplD): TypeSource()
    data class SpeechKitT(val item: SpeechKitImplD): TypeSource()
    data class SetT(val item: SetImplD): TypeSource()
    data class SetsT(val item: List<Set>): TypeSource()
    data class ActivityT(val item: ActivityImplD): TypeSource()
    data class ActivitiesT(val item: List<Activity>): TypeSource()
    data class ExerciseT(val item: ExerciseImplD): TypeSource()
    data class ExercisesT(val item: List<Exercise>): TypeSource()
    data class SetViewIdT(val item: SetViewIdD): TypeSource()
    data class RingT(val item: RingImplD): TypeSource()
    data class RingsT(val item: List<Ring>): TypeSource()
    data class RoundT(val item: RoundImplD): TypeSource()
    data class RoundsT(val item: List<Round>): TypeSource()
    data class PlanT(val item: TrainingImplD): TypeSource()
    data class PlansT(val item: List<Training>): TypeSource()
    data class StepPlanT(val item: StepPlan): TypeSource()
    data class SettingT(val item: Setting): TypeSource()
    data class SettingsT(val item: Settings): TypeSource()
    data class DeviceUIT(val item: DeviceBle): TypeSource()
    data class DevicesUIT(val item: Map<String, DeviceBle>): TypeSource()
    data class BleConnectStateT(val item: ConnectState): TypeSource()
    data class WeatherT(val item: Weather): TypeSource()
    data class WeatherRequestT(val item: WeatherRequest): TypeSource()
    data class DataForChangeSequenceT(val item: DataForChangeSequence): TypeSource()
    data object NullT: TypeSource()

    fun toRepo(): TypeRepo = when(this){
        is IntT -> TypeRepo.IntT(item = this.item)
        is LongT -> TypeRepo.LongT(item = this.item)
        is LongsT ->  TypeRepo.LongsT(item = this.item)
        is StringT -> TypeRepo.StringT(item = this.item)
        is BooleanT -> TypeRepo.BooleanT(item = this.item)
        is CollapsingT -> TypeRepo.CollapsingT(item = this.item)
        is ShowBottomSheetT -> TypeRepo.ShowBottomSheetT(item = this.item)
        is SpeechT -> TypeRepo.SpeechT(item = this.item)
        is SpeechKitT -> TypeRepo.SpeechKitT(item = this.item)
        is SetT -> TypeRepo.SetT(item = this.item)
        is SetsT -> TypeRepo.SetsT(item = this.item)
        is ActivitiesT -> TypeRepo.ActivitiesT(item = this.item)
        is ActivityT -> TypeRepo.ActivityT(item = this.item)
        is ExerciseT -> TypeRepo.ExerciseT(item = this.item)
        is ExercisesT -> TypeRepo.ExercisesT(item = this.item)
        is RingT -> TypeRepo.RingT(item = this.item)
        is RingsT -> TypeRepo.RingsT(item = this.item)
        is RoundT -> TypeRepo.RoundT(item = this.item)
        is RoundsT ->  TypeRepo.RoundsT(item = this.item)
        is PlanT -> TypeRepo.PlanT(item = this.item)
        is PlansT -> TypeRepo.PlansT(item = this.item)
        is StepPlanT -> TypeRepo.StepPlanT(item = this.item)
        is SettingT -> TypeRepo.SettingT(item = this.item)
        is SettingsT -> TypeRepo.SettingsT(item = this.item)
        is DeviceUIT -> TypeRepo.DeviceUIT(item = this.item)
        is DevicesUIT -> TypeRepo.DevicesUIT(item = this.item)
        is WeatherT -> TypeRepo.WeatherT(item = this.item)
        is WeatherRequestT -> TypeRepo.WeatherRequestT(item = this.item)
        is DataForChangeSequenceT -> TypeRepo.DataForChangeSequenceT(item = this.item)
        is BleConnectStateT -> TypeRepo.BleConnectStateT(item = this.item)
        NullT -> TypeRepo.NullT
        is SetViewIdT -> TypeRepo.NullT
    }
}
