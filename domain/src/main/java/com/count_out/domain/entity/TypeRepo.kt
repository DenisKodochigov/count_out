package com.count_out.domain.entity

import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit

sealed class TypeRepo {
    data class IntT(val item: Int): TypeRepo()
    data class LongT(val item: Long): TypeRepo()
    data class LongsT(val item: List<Long>): TypeRepo()
    data class StringT(val item: String): TypeRepo()
    data class BooleanT(val item: Boolean): TypeRepo()
    data class CollapsingT(val item: Collapsing): TypeRepo()
    data class ShowBottomSheetT(val item: ShowBottomSheet): TypeRepo()
    data class SpeechT(val item: Speech): TypeRepo()
    data class SpeechKitT(val item: SpeechKit): TypeRepo()
    data class SetT(val item: Set): TypeRepo()
    data class SetsT(val item: List<Set>): TypeRepo()
    data class ActivityT(val item: Activity): TypeRepo()
    data class ActivitiesT(val item: List<Activity>): TypeRepo()
    data class ExerciseT(val item: Exercise): TypeRepo()
    data class ExercisesT(val item: List<Exercise>): TypeRepo()
    data class RingT(val item: Ring): TypeRepo()
    data class RingsT(val item: List<Ring>): TypeRepo()
    data class PartT(val item: Part): TypeRepo()
    data class PartsT(val item: List<Part>): TypeRepo()
    data class PlanT(val item: Plan): TypeRepo()
    data class PlansT(val item: List<Plan>): TypeRepo()
    data class SetViewIdT(val item: SetViewId): TypeRepo()
    data class StepPlanT(val item: StepPlan): TypeRepo()
    data class SettingsT(val item: Settings): TypeRepo()
    data class DeviceUIT(val item: DeviceBle): TypeRepo()
    data class DevicesUIT(val item: Map<String, DeviceBle>): TypeRepo()
    data class BleConnectStateT(val item: ConnectState): TypeRepo()
    data class WeatherT(val item: Weather): TypeRepo()
    data class WeatherRequestT(val item: WeatherRequest): TypeRepo()
    data class SpeechesT(val item : List<Speech>): TypeRepo()
    data object NullT: TypeRepo()
    fun TypeRepo.toStepPlan(): TypeRepo{
        return if (this is PlanT) StepPlanT(item = GlobalValueApp.toStepPlan(this.item))
        else NullT
    }
}